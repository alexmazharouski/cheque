package service.purchase;

import entities.purchase.AbstractPurchase;
import entities.purchase.CardDiscountPurchase;
import entities.purchase.PercentDiscountPurchase;
import exceptions.NoSuchProductException;
import service.card.CardService;
import service.product.ProductService;
import util.Constants;
import entities.card.DiscountCard;
import entities.product.Product;
import entities.currency.Usd;

import java.util.ArrayList;
import java.util.List;

public class PurchasesApiService {
    private final List<AbstractPurchase> purchases = new ArrayList<>();
    private final ProductService productService;
    private final CardService cardService;

    public PurchasesApiService(ProductService productService, CardService cardService) {
        this.productService = productService;
        this.cardService = cardService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public List<AbstractPurchase> getPurchases() {
        return purchases;
    }

    public CardService getCardService() {
        return cardService;
    }

    public void addPurchasesToList(String[] array) {
        final int START_POSITION_OF_PURCHASES = 2;
        final String HAVING_CARD = "card";
        final int CARD_POSITION = 1;

        int numberOfPurchases = array.length - 1;
        DiscountCard discountCard = new DiscountCard();

        if (array[numberOfPurchases].contains(HAVING_CARD)) {
            String card = array[numberOfPurchases].split(Constants.DELIMITER)[CARD_POSITION];
            discountCard = cardService.searchCard(card);
            numberOfPurchases = numberOfPurchases - 1;
        }

        for (int i = START_POSITION_OF_PURCHASES; i <= numberOfPurchases; i++) {
            createPurchase(array[i], discountCard);
        }
    }


    public void createPurchase(String value, DiscountCard discountCard) {
        final int ID_POSITION = 0;
        final int COUNT_POSITION = 1;

        try {
            String[] valueArray = value.split(Constants.DELIMITER);
            int idOfProduct = Integer.parseInt(valueArray[ID_POSITION]);
            int numberOfUnits = Integer.parseInt(valueArray[COUNT_POSITION]);
            Product product = productService.searchProduct(idOfProduct);
            if (discountCard.getDiscountPercent() != 0) {
                purchases.add(new CardDiscountPurchase(product, numberOfUnits, discountCard));
            } else {
                purchases.add(new PercentDiscountPurchase(product, numberOfUnits));
            }
        } catch (NoSuchProductException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Purchase data is not correct");
        }
    }

    public Usd getTotalCost() {
        Usd totalCost = new Usd(0);
        for (AbstractPurchase purchase : purchases) {
            totalCost.add(purchase.getCost());
        }
        return totalCost;
    }

    public Usd getTotalCostWithDiscount() {
        Usd totalCostWithDiscount = new Usd(0);
        for (AbstractPurchase purchase : purchases) {
            totalCostWithDiscount.add(purchase.getFinalCost());
        }
        return totalCostWithDiscount;
    }

    public Usd getTotalSumOfDiscount() {
        return new Usd(getTotalCost()).sub(getTotalCostWithDiscount());
    }
}
