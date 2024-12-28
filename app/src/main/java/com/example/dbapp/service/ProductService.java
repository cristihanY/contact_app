package com.example.dbapp.service;

import java.util.List;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.model.entity.Customer;
import com.example.dbapp.model.entity.Product;
import com.example.dbapp.repository.dao.ProductDao;

public class ProductService {

    private final ProductDao productDao;

    public ProductService(AppDatabase database) {
        this.productDao = database.productDao();
    }

    public Product insert(Product product) {
        long productId = productDao.insert(product);
        return productDao.getById((long) productId);
    }

    public void update(Product product) {
        productDao.update(product);
    }


    public void updateProduct(long id, Product data) {
        Product product = productDao.getById(id);
        if (product != null) {
            product.setCategoryId(data.getCategoryId());
            product.setName(data.getName());
            product.setPrice(data.getPrice());
            product.setQuantity(data.getQuantity());
            product.setImg(data.getImg());
            product.setBarcode(data.getBarcode());
            product.setCostPrice(data.getCostPrice());
            product.setDescription(data.getDescription());
            product.setCreatedAt(data.getCreatedAt());

            productDao.update(product);
        }
    }

    public void delete(Product product) {
        productDao.delete(product);
    }

    public void removeProduct(long id) {
        productDao.deleteById(id);
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

    public Product getById(long id) {
        return productDao.getById(id);
    }

}
