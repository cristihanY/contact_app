package com.example.dbapp.core.init;

import androidx.room.Room;
import android.app.Application;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.service.CartService;
import com.example.dbapp.service.ProductService;
import com.example.dbapp.service.CustomerService;
import com.example.dbapp.service.CartItemService;

public class MyApp extends Application {

    private static MyApp instance;
    private AppDatabase database;
    private CustomerService customerService;
    private ProductService productService;
    private CartService cartService;
    private CartItemService cartItemService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-database").build();

        customerService = new CustomerService(database);
        productService = new ProductService(database);
        cartItemService = new CartItemService(database, productService);
        cartService = new CartService(database, cartItemService);
    }

    public static MyApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public CartItemService getCartItemService() {
        return cartItemService;
    }

}
