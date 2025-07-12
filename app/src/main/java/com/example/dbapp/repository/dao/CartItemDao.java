package com.example.dbapp.repository.dao;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import com.example.dbapp.model.entity.CartItem;

@Dao
public interface CartItemDao {

    @Query("SELECT * FROM cart_items")
    List<CartItem> getAll();

    @Query("SELECT * FROM cart_items WHERE id = :id")
    CartItem getById(long id);

    @Query("SELECT * FROM cart_items WHERE cartId = :cartId")
    List<CartItem> getByCartId(long cartId);

    @Query("SELECT SUM(quantity) FROM cart_items WHERE cartId = :cartId")
    int getTotalQuantityByCartId(long cartId);

    @Insert
    long insert(CartItem item);

    @Insert
    List<Long> insertAll(List<CartItem> items);

    @Update
    void update(CartItem item);

    @Delete
    void delete(CartItem item);

    @Query("DELETE FROM cart_items WHERE id = :id")
    void deleteById(long id);

    @Query("DELETE FROM cart_items WHERE cartId = :cartId")
    void deleteByCartId(long cartId);
}

