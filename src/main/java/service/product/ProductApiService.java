package service.product;

import exceptions.NoSuchProductException;
import entities.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductApiService implements ProductService {
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void createProducts() {
        addDefaultProductsToList();
    }

    @Override
    public Product searchProduct(int idOfProduct) {
        for (Product product : products) {
            if (product.getId() == idOfProduct) {
                return product;
            }
        }
        throw new NoSuchProductException("Check product Id");
    }

    private void addDefaultProductsToList() {
        products.add(new Product(1, "Laptop", 55000));
        products.add(new Product(2, "Phone", 27000));
        products.add(new Product(3, "Computer", 93500));
        products.add(new Product(4, "TV", 63500));
        products.add(new Product(5, "Bicycle", 78900));
    }
}
