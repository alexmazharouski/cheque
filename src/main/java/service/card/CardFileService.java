package service.card;

import util.Constants;
import entities.card.DiscountCard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardFileService extends CardApiService {

    private final String filePath;

    public CardFileService(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void createCards() {
        try {
            if (filePath.equals(Constants.CARD_FILE_ABSENT)) {
                addDefaultCardsToList();
            } else {
                readCardsFile();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File with cards not found. Default values will be used.");
            addDefaultCardsToList();
        }
    }


    public void readCardsFile() throws FileNotFoundException {
        final String pathFile = filePath.split(Constants.DELIMITER)[Constants.POSITION_PATH_FILE];
        final int NUMBER_OF_CARD_POSITION = 0;
        final int DISCOUNT_PERCENT_POSITION = 1;
        Scanner scanner = new Scanner(new File(pathFile));
        while (scanner.hasNext()) {
            try {
                String[] arrayOfValues = scanner.nextLine().split(Constants.DELIMITER);
                int numberOfCard = Integer.parseInt(arrayOfValues[NUMBER_OF_CARD_POSITION]);
                double discountPercent = Double.parseDouble(arrayOfValues[DISCOUNT_PERCENT_POSITION]);
                super.getCards().add(new DiscountCard(numberOfCard, discountPercent));
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid card data in file.");
            }
        }
        scanner.close();
    }
}
