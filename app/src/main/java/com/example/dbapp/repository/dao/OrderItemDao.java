package com.example.dbapp.repository.dao;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import com.example.dbapp.model.entity.OrderItem;


@Dao
public interface OrderItemDao {

    @Query("SELECT * FROM order_items")
    List<OrderItem> getAll();

    @Query("SELECT * FROM order_items WHERE id = :id")
    OrderItem getById(long id);

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    List<OrderItem> getByOrderId(long orderId);

    @Insert
    long insert(OrderItem item);

    @Insert
    List<Long> insertAll(List<OrderItem> items);

    @Update
    void update(OrderItem item);

    @Delete
    void delete(OrderItem item);

    @Query("DELETE FROM order_items WHERE id = :id")
    void deleteById(long id);

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    void deleteByOrderId(long orderId);
}

