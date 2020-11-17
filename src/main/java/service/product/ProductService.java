package service.product;

import entities.product.Product;

public interface ProductService {
    void createProducts();
    Product searchProduct(int idOfProduct);
}
