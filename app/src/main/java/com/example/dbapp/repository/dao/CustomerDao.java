package com.example.dbapp.repository.dao;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Transaction;
import com.example.dbapp.model.entity.Customer;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM customers")
    List<Customer> getAll();

    @Query("SELECT * FROM customers ORDER BY date DESC")
    List<Customer> getAllFirst();

    @Query("SELECT * FROM customers WHERE id = :id")
    Customer getById(long id);

    @Insert
    long insert(Customer customer);

    @Transaction
    default Customer persist(Customer customer) {
        long contactId = insert(customer);
        return getById((int) contactId);
    }

    @Update
    void update(Customer customer);

    @Transaction
    default Customer edit(Customer customer) {
        update(customer);
        return getById(customer.getId());
    }

    @Delete
    void delete(Customer customer);

    @Query("DELETE FROM customers WHERE id = :id")
    void deleteById(long id);
}
