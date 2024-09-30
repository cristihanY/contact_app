package com.example.dbapp.service;

import java.util.List;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.model.entity.Product;
import com.example.dbapp.repository.ProductRepository;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(AppDatabase database) {
        this.productRepository = new ProductRepository(database);
    }

    public void addProduct(Product product) {
        Product p = productRepository.insert(product);
        System.out.println("CREATE " + p.getName());
    }

    public void updateProduct(Product product) {
        productRepository.update(product);
    }

    public void removeProduct(Product product) {
        productRepository.delete(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAll();
    }

    public Product getProductById(int id) {
        return productRepository.getById(id);
    }

}
