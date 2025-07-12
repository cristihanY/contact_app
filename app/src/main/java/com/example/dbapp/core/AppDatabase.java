package com.example.dbapp.core;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.dbapp.model.entity.Cart;
import com.example.dbapp.model.entity.Order;
import com.example.dbapp.model.entity.Product;
import com.example.dbapp.core.util.Converters;
import com.example.dbapp.model.entity.CartItem;
import com.example.dbapp.model.entity.Customer;
import com.example.dbapp.repository.dao.CartDao;
import com.example.dbapp.model.entity.OrderItem;
import com.example.dbapp.repository.dao.OrderDao;
import com.example.dbapp.repository.dao.ProductDao;
import com.example.dbapp.repository.dao.CartItemDao;
import com.example.dbapp.repository.dao.CustomerDao;
import com.example.dbapp.repository.dao.OrderItemDao;
import com.example.dbapp.converter.BigDecimalConverter;

@Database(entities = {Customer.class, Product.class, Order.class, OrderItem.class, Cart.class, CartItem.class}, version = 1)
@TypeConverters({Converters.class, BigDecimalConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();
    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();
    public abstract OrderItemDao orderItemDao();
    public abstract CartDao cartDao();
    public abstract CartItemDao cartItemDao();
}
