package hr.foi.air.database.data;

import android.content.Context;

import hr.foi.air.database.DAO;
import hr.foi.air.database.MyDatabase;
import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.database.entities.Results;

public class MockData {
    private static DAO dao;

    public static void writeAll(Context context)
    {
        //get dao
        dao = MyDatabase.getInstance(context).getDAO();

        Picture nekaSlika = new Picture();
        nekaSlika.setImageUri("http://nekakvaAdresa.com");
        nekaSlika.setId((int)dao.insertPictures(nekaSlika)[0]);

        Product zelenaCigla = new Product();
        zelenaCigla.setProductName("super zelena cigla");
        zelenaCigla.setDescription("najbolja cigla na svijetu");
        zelenaCigla.setFlagFavorite(555);
        zelenaCigla.setId((int)dao.insertProducts(zelenaCigla)[0]);

        Results results = new Results();
        results.setIdPicture(nekaSlika.getId());
        results.setIdProduct(zelenaCigla.getId());
        dao.insertResults(results);
    }


}
