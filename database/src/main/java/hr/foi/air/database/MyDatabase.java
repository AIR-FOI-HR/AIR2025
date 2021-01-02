package hr.foi.air.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.database.entities.Results;

@Database(version = 1, entities = {Picture.class, Product.class, Results.class}, views = {}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public static final String NAME = "main";
    public static final int VERSION = 1;

    private static MyDatabase INSTANCE = null;

    public static synchronized MyDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    MyDatabase.NAME
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract DAO getDAO();
}
