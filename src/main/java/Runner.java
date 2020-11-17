import service.purchase.PurchasesApiService;
import service.card.CardFileService;
import service.card.CardService;
import service.output.OutputService;
import service.product.ProductFileService;
import service.product.ProductService;

public class Runner {

    public static void main(String[] args) {
        final int PRODUCT_FILE_PATH_POSITION = 0;
        final int CARD_FILE_PATH_POSITION = 1;

        String productFilePath = args[PRODUCT_FILE_PATH_POSITION];
        String cardFilePath = args[CARD_FILE_PATH_POSITION];

        ProductService productService = new ProductFileService(productFilePath);
        CardService cardService = new CardFileService(cardFilePath);
        productService.createProducts();
        cardService.createCards();

        PurchasesApiService controller = new PurchasesApiService(productService, cardService);
        controller.addPurchasesToList(args);

        OutputService.writeToConsole(controller);
        OutputService.writePdfFile(controller);
    }
}