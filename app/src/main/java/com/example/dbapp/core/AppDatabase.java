package com.example.dbapp.core;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.dbapp.core.util.Converters;
import com.example.dbapp.model.entity.Contact;
import com.example.dbapp.model.entity.Product;
import com.example.dbapp.repository.dao.ContactDao;
import com.example.dbapp.repository.dao.ProductDao;

@Database(entities = {Contact.class, Product.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract ProductDao productDao();
}
