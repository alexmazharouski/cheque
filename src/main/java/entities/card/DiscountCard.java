package entities.card;

public class DiscountCard {
    private final int numberOfCard;
    private final double discountPercent;

    public DiscountCard() {
        this(0, 0);
    }

    public DiscountCard(int numberOfCard, double discountPercent) {
        this.numberOfCard = numberOfCard;
        this.discountPercent = discountPercent;
    }

    public int getNumberOfCard() {
        return numberOfCard;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return numberOfCard == that.numberOfCard &&
                Double.compare(that.discountPercent, discountPercent) == 0;
    }
}
