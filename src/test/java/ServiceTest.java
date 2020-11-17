import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.card.CardApiService;
import service.card.CardFileService;
import service.product.ProductApiService;
import service.product.ProductFileService;
import entities.card.DiscountCard;
import entities.product.Product;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ServiceTest {
    private List<Product> products;
    private List<DiscountCard> cards;

    @Before
    public void addParameters() {
        products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 55000));
        products.add(new Product(2, "Phone", 27000));
        products.add(new Product(3, "Computer", 93500));
        products.add(new Product(4, "TV", 63500));
        products.add(new Product(5, "Bicycle", 78900));

        cards = new ArrayList<>();
        cards.add(new DiscountCard(1234, 20));
        cards.add(new DiscountCard(1235, 7.5));
        cards.add(new DiscountCard(1236, 11));
    }

    @Test
    public void checkDefaultValuesOfCards(){
        CardApiService cardService = new CardApiService();
        cardService.createCards();
        Assert.assertEquals(cardService.getCards(), cards);
    }

    @Test
    public void checkDefaultValuesOfProducts(){
        ProductApiService productService = new ProductApiService();
        productService.createProducts();
        Assert.assertEquals(productService.getProducts(), products);
    }

    @Test(expected = FileNotFoundException.class)
    public void readProductFileShouldThrowException() throws FileNotFoundException {
        ProductFileService productFileService = new ProductFileService("productFile;file");
        productFileService.readProductFile();
    }

    @Test(expected = FileNotFoundException.class)
    public void readCardsFileShouldThrowException() throws FileNotFoundException {
        CardFileService cardFileService = new CardFileService("cardFile;file");
        cardFileService.readCardsFile();
    }
}
