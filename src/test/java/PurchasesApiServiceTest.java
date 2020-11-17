import service.purchase.PurchasesApiService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.card.CardApiService;
import service.card.CardService;
import service.product.ProductApiService;
import service.product.ProductService;
import entities.card.DiscountCard;

public class PurchasesApiServiceTest {
    private PurchasesApiService purchasesService;
    private String[] arrayOfValues;

    @Before
    public void setUp() {
        ProductService productService = new ProductApiService();
        productService.createProducts();
        CardService cardService = new CardApiService();
        cardService.createCards();

        purchasesService = new PurchasesApiService(productService, cardService);
        arrayOfValues = new String[]{"productFile;absent", "cardFile;absent", "1;5", "3;4", "5;10"};
    }

    @Test
    public void getAllPurchasesNotNull() {
        Assert.assertNotNull(purchasesService.getPurchases());
    }

    @Test
    public void getHowManyPurchases() {
        purchasesService.addPurchasesToList(arrayOfValues);
        int expected = purchasesService.getPurchases().size();
        int actual = 3;

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void searchProductWithNotExistIdShouldThrowException() {
        purchasesService.getProductService().searchProduct(100);
    }

    @Test
    public void searchCardShouldGetEmptyCard() {
        DiscountCard expected = purchasesService.getCardService().searchCard("1");
        DiscountCard actual = new DiscountCard();

        Assert.assertEquals(expected, actual);
    }
}
