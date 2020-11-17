package entities.purchase;

import entities.product.Product;
import entities.currency.Usd;

import java.util.Objects;

public abstract class AbstractPurchase {
    private Product product;
    private int count;

    public AbstractPurchase(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public Usd getCost(){
        Usd total = new Usd(product.getPrice());
        return total.multiply(count);
    }

    public abstract Usd getFinalCost();

    protected String fieldsToString() {
        return String.format(" %-4d%-10s%-10s", count, product.getName(), product.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPurchase purchase = (AbstractPurchase) o;
        return count == purchase.count &&
                Objects.equals(product, purchase.product);
    }

    @Override
    public String toString() {
        return String.format("%s%-10s%-10s", fieldsToString(), getCost(), getFinalCost());
    }
}

