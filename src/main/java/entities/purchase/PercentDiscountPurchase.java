package entities.purchase;

import entities.product.Product;
import entities.currency.Usd;

public class PercentDiscountPurchase extends AbstractPurchase {
    private static final int MIN_NUMBER_OF_UNITS = 5;
    private static final double DISCOUNT_PERCENT = 10;

    public PercentDiscountPurchase(Product product, int numberOfUnits) {
        super(product, numberOfUnits);
    }

    @Override
    public Usd getFinalCost() {
        Usd total = new Usd(super.getCost());
        if (getCount() > MIN_NUMBER_OF_UNITS) {
            total.multiplyRound(DISCOUNT_PERCENT);
        }
        return total;
    }
}
