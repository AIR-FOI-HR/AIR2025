package hr.foi.air.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.database.entities.Results;

@Database(version = 1, entities = {Picture.class, Product.class, Results.class}, views = {}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

}
