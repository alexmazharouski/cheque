package service.product;

import util.Constants;
import entities.product.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProductFileService extends ProductApiService {

    private final String filePath;

    public ProductFileService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void createProducts() {
        try {
            if (filePath.equals(Constants.PRODUCT_FILE_ABSENT)) {
                super.createProducts();
            } else {
                readProductFile();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File with products not found. Default values will be used.");
            super.createProducts();
        }
    }

    public void readProductFile() throws FileNotFoundException {
        final String pathFile = filePath.split(Constants.DELIMITER)[Constants.POSITION_PATH_FILE];
        final int ID_POSITION = 0;
        final int NAME_POSITION = 1;
        final int PRICE_POSITION = 2;

        Scanner scanner = new Scanner(new File(pathFile));
        while (scanner.hasNext()) {
            try {
                String[] arrayOfValues = scanner.nextLine().split(Constants.DELIMITER);
                int id = Integer.parseInt(arrayOfValues[ID_POSITION]);
                int priceOfProduct = Integer.parseInt(arrayOfValues[PRICE_POSITION]);
                super.getProducts().add(new Product(id, arrayOfValues[NAME_POSITION], priceOfProduct));
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid product data in file.");
            }
        }
        scanner.close();
    }
}
