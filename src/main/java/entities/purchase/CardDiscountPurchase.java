package entities.purchase;

import entities.card.DiscountCard;
import entities.product.Product;
import entities.currency.Usd;

public class CardDiscountPurchase extends AbstractPurchase {
    private final DiscountCard discountCard;

    public CardDiscountPurchase(Product product, int numberOfUnits, DiscountCard discountCard) {
        super(product, numberOfUnits);
        this.discountCard = discountCard;
    }

    @Override
    public Usd getFinalCost() {
        Usd total = new Usd(super.getCost());
        return total.multiplyRound(discountCard.getDiscountPercent());
    }
}
