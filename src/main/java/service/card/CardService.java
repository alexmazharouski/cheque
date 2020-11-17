package service.card;

import entities.card.DiscountCard;

public interface CardService {
    void createCards();
    DiscountCard searchCard(String discountCard);
}
