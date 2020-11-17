package service.output;

import service.pdf.PdfGeneration;
import util.Constants;
import service.purchase.PurchasesApiService;
import entities.purchase.AbstractPurchase;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputService {
    private static final StringBuilder CHEQUE = new StringBuilder();
    private static final String FILE_PATH = "src/result.pdf";
    private static final String TITLE_FORMAT = "%s\n%s\n";
    private static final String TABLE_HEADER_FORMAT = "%-5s%-10s%-10s%-10s%s\n";
    private static final String RESULTS_FORMAT = "%-34s%s\n%-34s%s\n%-34s%s";

    public static void writeToConsole(PurchasesApiService controller) {
        String firstLine = String.format(TITLE_FORMAT, Constants.CHEQUE_TITLE, Constants.CHEQUE_SUPERMARKET);
        String dateLine = String.format(TITLE_FORMAT, Constants.DATE + getDateTime(Constants.DATE_PATTERN),
                Constants.TIME + getDateTime(Constants.TIME_PATTERN));
        String headerLine = String.format(TABLE_HEADER_FORMAT, Constants.CHEQUE_QTY, Constants.CHEQUE_PRODUCT,
                Constants.CHEQUE_PRICE, Constants.CHEQUE_COST, Constants.CHEQUE_TOTAL);

        CHEQUE.append(firstLine).append(dateLine).append(headerLine);
        createLine(headerLine.length());

        for (AbstractPurchase purchase : controller.getPurchases()) {
            CHEQUE.append(purchase.toString()).append("\n");
        }

        createLine(headerLine.length());
        CHEQUE.append(String.format(RESULTS_FORMAT, Constants.CHEQUE_COST, controller.getTotalCost(),
                Constants.CHEQUE_DISCOUNT, controller.getTotalSumOfDiscount(), Constants.CHEQUE_TOTAL,
                controller.getTotalCostWithDiscount()));

        System.out.println(CHEQUE);
    }

    public static void writePdfFile(PurchasesApiService controller) {
        PdfGeneration pdfGeneration = new PdfGeneration();
        ByteArrayOutputStream byteArrayOutputStream = pdfGeneration.createPdf(controller);
        try(OutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            byteArrayOutputStream.writeTo(outputStream);
        } catch (IOException e) {
            System.out.println("File is not available!");
        }
    }

    private static void createLine (int length) {
        for (int i = 0; i < length; i++) {
            CHEQUE.append("-");
        }
        CHEQUE.append("\n");
    }

    public static String getDateTime(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }
}
