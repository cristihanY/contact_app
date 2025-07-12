package com.example.dbapp.repository.dao;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dbapp.model.entity.Cart;


@Dao
public interface CartDao {

    @Query("SELECT * FROM carts")
    List<Cart> getAll();

    @Query("SELECT * FROM carts WHERE id = :id")
    Cart getById(long id);

    @Insert
    long insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM carts WHERE id = :id")
    void deleteById(long id);
}
