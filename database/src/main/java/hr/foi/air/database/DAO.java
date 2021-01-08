package hr.foi.air.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.database.entities.Results;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertProducts(Product... products);
    @Update public void updateProducts(Product... products);
    @Delete public void deleteProducts(Product... products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertPictures(Picture... pictures);
    @Update public void updatePictures(Picture... pictures);
    @Delete public void deleteProducts(Picture... pictures);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertResults(Results... results);
    @Update public void updateResults(Results... results);
    @Delete public void deleteResults(Results... results);

    @Query("SELECT * FROM pictures ORDER BY pictureDate DESC")
    public List<Picture> loadAllPictures();

    @Query("SELECT * FROM products JOIN results ON products.id = results.idProduct WHERE results.idPicture = :pictureId")
    public List<Product> loadRelevantProducts(int pictureId);


}
