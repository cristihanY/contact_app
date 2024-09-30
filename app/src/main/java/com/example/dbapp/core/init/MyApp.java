package com.example.dbapp.core.init;



import androidx.room.Room;
import android.app.Application;
import com.example.dbapp.core.AppDatabase;

public class MyApp extends Application {

    private static MyApp instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "my-database").build();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

}
