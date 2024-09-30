package com.example.dbapp.repository;

import java.util.List;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.model.entity.Product;
import com.example.dbapp.repository.dao.ProductDao;

public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(AppDatabase database) {
        this.productDao = database.productDao();
    }

    public Product insert(Product product) {
        long productId = productDao.insert(product);
        return productDao.getById((int) productId);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(Product product) {
        productDao.delete(product);
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public Product getById(int id) {
        return productDao.getById(id);
    }

}
