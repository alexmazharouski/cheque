package entities.product;

import entities.currency.Usd;

import java.util.Objects;

public final class Product {
    private final int id;
    private final String name;
    private final Usd price;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = new Usd(price);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Usd getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price);
    }

    @Override
    public String toString() {
        return name + " " + price;
    }
}
