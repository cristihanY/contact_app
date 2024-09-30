package com.example.dbapp.repository.dao;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Transaction;
import com.example.dbapp.model.entity.Contact;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contacts")
    List<Contact> getAll();

    @Query("SELECT * FROM contacts WHERE id = :id")
    Contact getById(int id);

    @Insert
    long insert(Contact contact);

    @Transaction
    default Contact persist(Contact contact) {
        long contactId = insert(contact);
        return getById((int) contactId);
    }

    @Update
    void update(Contact contact);

    @Transaction
    default Contact edit(Contact contact) {
        update(contact);
        return getById(contact.getId());
    }

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contacts WHERE id = :id")
    void deleteById(int id);
}
