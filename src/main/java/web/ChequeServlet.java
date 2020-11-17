package web;

import service.purchase.PurchasesApiService;
import service.card.CardApiService;
import service.card.CardService;
import service.pdf.PdfGeneration;
import service.product.ProductApiService;
import service.product.ProductService;
import entities.card.DiscountCard;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ChequeServlet extends HttpServlet {
    private final CardService cardService = new CardApiService();
    private final ProductService productService = new ProductApiService();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cardService.createCards();
        productService.createProducts();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String PRODUCT_PARAMETER = "itemId";
        final String CARD_PARAMETER = "cardId";

        String[] itemArray = request.getParameterValues(PRODUCT_PARAMETER);
        String cardId = request.getParameter(CARD_PARAMETER);

        PurchasesApiService purchasesApiService = new PurchasesApiService(productService, cardService);

        createPurchasesList(itemArray, cardId, purchasesApiService);

        ServletOutputStream outputStream = response.getOutputStream();
        PdfGeneration pdfGeneration = new PdfGeneration();
        ByteArrayOutputStream byteArrayOutputStream = pdfGeneration.createPdf(purchasesApiService);
        byteArrayOutputStream.writeTo(outputStream);
        byteArrayOutputStream.close();
    }

    private void createPurchasesList(String[] itemArray, String cardId, PurchasesApiService purchasesApiService) {
        DiscountCard card = purchasesApiService.getCardService().searchCard(cardId);

        for (String value : itemArray) {
            purchasesApiService.createPurchase(value, card);
        }
    }
}
