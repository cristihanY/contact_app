package com.example.dbapp.repository.dao;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import com.example.dbapp.model.entity.Order;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query("SELECT * FROM orders WHERE id = :id")
    Order getById(long id);

    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("DELETE FROM orders WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT * FROM orders WHERE customerId = :customerId")
    List<Order> getByCustomerId(long customerId);
}

