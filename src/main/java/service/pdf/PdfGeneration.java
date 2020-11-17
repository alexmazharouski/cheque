package service.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import service.output.OutputService;
import util.Constants;
import service.purchase.PurchasesApiService;
import entities.purchase.AbstractPurchase;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGeneration {
    private final PdfPTable table = new PdfPTable(5);
    private static final int TABLE_COLUMNS = 5;
    private static final int TABLE_WIDTH_PERCENT = 35;
    private static final int COLUMN_NUMBER_WIDTH = 1;
    private static final int COLUMN_WIDTH = 2;
    private static final int FONT_SIZE_TITLE = 14;
    private static final int FONT_SIZE_HEADERS = 6;
    private static final int FONT_SIZE_RESULTS = 7;
    private static final int BORDER_WIDTH = 0;
    private static final float LINE_WIDTH = 0.5f;
    private static final int PADDING = 10;
    private static final int COLUMN_SPAN_3 = 3;
    private static final int COLUMN_SPAN_2 = 2;

    public ByteArrayOutputStream createPdf(PurchasesApiService controller) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            table.setWidthPercentage(TABLE_WIDTH_PERCENT);
            table.setWidths(new int[]{COLUMN_NUMBER_WIDTH, COLUMN_WIDTH, COLUMN_WIDTH, COLUMN_WIDTH, COLUMN_WIDTH});

            Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD, FONT_SIZE_TITLE, BaseColor.BLACK);
            Font fontHeaders = FontFactory.getFont(FontFactory.TIMES_BOLD, FONT_SIZE_HEADERS, BaseColor.BLACK);
            Font fontTotalResult = FontFactory.getFont(FontFactory.TIMES_BOLD, FONT_SIZE_RESULTS, BaseColor.BLACK);

            addHeadersRows(Constants.CHEQUE_TITLE, fontTitle);

            addHeadersRows(Constants.CHEQUE_SUPERMARKET, fontHeaders);

            addHeadersRows(Constants.CHEQUE_ADDRESS, fontHeaders);

            addHeadersRows(Constants.CHEQUE_PHONE, fontHeaders);

            PdfPCell cell = new PdfPCell(new Phrase(Constants.CHEQUE_CASHIER, fontHeaders));
            cell.setColspan(COLUMN_SPAN_3);
            cell.setRowspan(COLUMN_SPAN_2);
            cell.setBorderWidth(BORDER_WIDTH);
            table.addCell(cell);

            PdfPCell cellDate = new PdfPCell(new Phrase(Constants.DATE +
                    OutputService.getDateTime(Constants.DATE_PATTERN), fontHeaders));
            cellDate.setColspan(COLUMN_SPAN_2);
            cellDate.setBorderWidth(BORDER_WIDTH);
            cellDate.setPaddingLeft(PADDING);
            table.addCell(cellDate);

            PdfPCell cellTime = new PdfPCell(new Phrase(Constants.TIME +
                    OutputService.getDateTime(Constants.TIME_PATTERN), fontHeaders));
            cellTime.setColspan(COLUMN_SPAN_2);
            cellTime.setBorderWidth(BORDER_WIDTH);
            cellTime.setPaddingLeft(PADDING);
            table.addCell(cellTime);

            addHeadersOfTable(Constants.CHEQUE_QTY, fontHeaders);

            addHeadersOfTable(Constants.CHEQUE_PRODUCT, fontHeaders);

            addHeadersOfTable(Constants.CHEQUE_PRICE, fontHeaders);

            addHeadersOfTable(Constants.CHEQUE_COST, fontHeaders);

            addHeadersOfTable(Constants.CHEQUE_TOTAL, fontHeaders);

            addPurchasesToTable(controller.getPurchases());

            PdfPCell cellCost = new PdfPCell(new Phrase(Constants.CHEQUE_COST, fontHeaders));
            cellCost.setColspan(COLUMN_SPAN_3);
            cellCost.setBorderWidth(BORDER_WIDTH);
            cellCost.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellCost.setBorderWidthTop(LINE_WIDTH);
            table.addCell(cellCost);

            PdfPCell cellCostSum = new PdfPCell(new Phrase(controller.getTotalCost().toString(), fontHeaders));
            cellCostSum.setColspan(COLUMN_SPAN_2);
            cellCostSum.setBorderWidth(BORDER_WIDTH);
            cellCostSum.setBorderWidthTop(LINE_WIDTH);
            cellCostSum.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellCostSum);

            PdfPCell cellDiscount = new PdfPCell(new Phrase(Constants.CHEQUE_DISCOUNT, fontHeaders));
            cellDiscount.setColspan(COLUMN_SPAN_3);
            cellDiscount.setBorderWidth(BORDER_WIDTH);
            cellDiscount.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellDiscount);

            PdfPCell cellDiscountSum = new PdfPCell(new Phrase(controller.getTotalSumOfDiscount().toString(), fontHeaders));
            cellDiscountSum.setColspan(COLUMN_SPAN_2);
            cellDiscountSum.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellDiscountSum.setBorderWidth(BORDER_WIDTH);
            table.addCell(cellDiscountSum);

            PdfPCell cellTotal = new PdfPCell(new Phrase(Constants.CHEQUE_TOTAL, fontTotalResult));
            cellTotal.setColspan(COLUMN_SPAN_3);
            cellTotal.setBorderWidth(BORDER_WIDTH);
            cellTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cellTotal);

            PdfPCell cellTotalSum = new PdfPCell(new Phrase(controller.getTotalCostWithDiscount().toString(), fontTotalResult));
            cellTotalSum.setColspan(COLUMN_SPAN_2);
            cellTotalSum.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellTotalSum.setBorderWidth(BORDER_WIDTH);
            table.addCell(cellTotalSum);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            System.out.println("The document has been closed you cant add any elements.");
        }
        return byteArrayOutputStream;
    }

    private void addPurchasesToTable(List<AbstractPurchase> purchases) {
        Font fontOfList = FontFactory.getFont(FontFactory.TIMES, FONT_SIZE_RESULTS, BaseColor.BLACK);

        for (AbstractPurchase purchase : purchases) {
            createPurchasesCell(String.valueOf(purchase.getCount()), fontOfList, 0, Element.ALIGN_CENTER);
            createPurchasesCell(purchase.getProduct().getName(), fontOfList, PADDING, Element.ALIGN_LEFT);
            createPurchasesCell(purchase.getProduct().getPrice().toString(), fontOfList, 0, Element.ALIGN_RIGHT);
            createPurchasesCell(purchase.getCost().toString(), fontOfList, 0, Element.ALIGN_RIGHT);
            createPurchasesCell(purchase.getFinalCost().toString(), fontOfList, 0, Element.ALIGN_RIGHT);
        }
    }

    private void createPurchasesCell(String value, Font font, int padding, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setBorderWidth(BORDER_WIDTH);
        cell.setHorizontalAlignment(align);
        cell.setPaddingLeft(padding);
        table.addCell(cell);
    }

    private void addHeadersRows(String header, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(header, font));
        cell.setColspan(TABLE_COLUMNS);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidth(BORDER_WIDTH);
        table.addCell(cell);
    }

    private void addHeadersOfTable(String header, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(header, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderWidth(BORDER_WIDTH);
        cell.setBorderWidthTop(LINE_WIDTH);
        cell.setPaddingBottom(PADDING);
        table.addCell(cell);
    }
}
