package service.card;

import entities.card.DiscountCard;

import java.util.ArrayList;
import java.util.List;


public class CardApiService implements CardService {
    private final List<DiscountCard> cards = new ArrayList<>();

    public List<DiscountCard> getCards() {
        return cards;
    }

    @Override
    public void createCards() {
        addDefaultCardsToList();
    }

    @Override
    public DiscountCard searchCard(String discountCard) {
        try {
            int numberOfCard = Integer.parseInt(discountCard);
            for (DiscountCard card : cards) {
                if (card.getNumberOfCard() == numberOfCard) {
                    return card;
                }
            }
        } catch (NumberFormatException e) {
            return new DiscountCard();
        }
        return new DiscountCard();
    }

    protected void addDefaultCardsToList() {
        cards.add(new DiscountCard(1234, 20));
        cards.add(new DiscountCard(1235, 7.5));
        cards.add(new DiscountCard(1236, 11));
    }

}
