package hr.foi.air.visualbrickfinder.webservice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.foi.air.database.DAO;
import hr.foi.air.database.MyDatabase;
import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.database.entities.Results;
import hr.foi.air.visualbrickfinder.SimilarProductsFragment;
import hr.foi.air.webservicefrontend.VbfWebserviceCaller;
import hr.foi.air.webservicefrontend.VbfWebserviceHandler;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class SimilarProductsStorage {

    private  static DAO dao;
    private List<Brick> bricks;
    private List<RoofTile> roofTiles;
    private SimilarProductsFragment caller;
    private long insertionTimestamp;


    public void getProducts(SimilarProductsFragment caller, Uri pictureUri) {
        this.caller = caller;

        if (isNetworkAvailable()) {
            loadData(pictureUri);
        } else {
            caller.cantReceiveProducts("Check your internet connection.");
        }
    }

    private void loadData(Uri pictureUri) {
        VbfWebserviceCaller productsCaller = new VbfWebserviceCaller(productsHandler);
        productsCaller.getAllSimilarProducts(pictureUri);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) caller.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
    These two methods are here only for future database improvements
     */
    private void returnSimilarRoofTiles() {
        //Here you can handle updating local database later on
        //Merge this and returnSimilarBricks() in one if needed
        caller.receiveRoofTiles(roofTiles);
        saveProductImagesRoofTiles(roofTiles,caller.imageUriReference);
    }



    private void returnSimilarBricks() {
        //Here you can handle updating local database later on
        //Merge this and returnSimilarRoofTiles() in one if needed
        caller.receiveBricks(bricks);
        saveProductImagesBricks(bricks,caller.imageUriReference);
    }


    private void saveProductImagesBricks(List<Brick> bricks, Uri imageUriReference) {
        dao = MyDatabase.getInstance(caller.getContext()).getDAO();

        Picture insertionPicture = new Picture();
        insertionPicture.setImageUri(imageUriReference.toString());
        insertionPicture.setPictureDate(new Date(insertionTimestamp));
        insertionPicture.setId((int)dao.insertPictures(insertionPicture)[0]);;

        for (Brick brick: bricks ) {
            Product insertionProduct = new Product();
            insertionProduct.setId(brick.getId());
            insertionProduct.setProductName(brick.getName());
            insertionProduct.setDimensions(null);
            insertionProduct.setBrand(brick.getBrand());
            insertionProduct.setDescription(brick.getDescription());
            insertionProduct.setFlagFavorite(0);
            insertionProduct.setProductImage(brick.getLocalImageUrl());
            dao.insertProducts(insertionProduct);
            Results result = new Results();
            result.setIdPicture(insertionPicture.getId());
            result.setIdProduct(insertionProduct.getId());
            dao.insertResults(result);
        }

    }

    private void saveProductImagesRoofTiles(List<RoofTile> roofTiles, Uri imageUriReference) {

        dao = MyDatabase.getInstance(caller.getContext()).getDAO();

        Picture insertionPicture = new Picture();
        insertionPicture.setImageUri(imageUriReference.toString());
        insertionPicture.setPictureDate(new Date(insertionTimestamp));
        insertionPicture.setId((int)dao.insertPictures(insertionPicture)[0]);;

        for (RoofTile roofTile: roofTiles ) {
            Product insertionProduct = new Product();
            insertionProduct.setId(roofTile.getId());
            insertionProduct.setProductName(roofTile.getName());
            insertionProduct.setDimensions(roofTile.getDimensions());
            insertionProduct.setBrand(roofTile.getBrand());
            insertionProduct.setDescription(roofTile.getDescription());
            insertionProduct.setFlagFavorite(0);
            insertionProduct.setProductImage(roofTile.getLocalImageUrl());
            dao.insertProducts(insertionProduct);
            Results result = new Results();
            result.setIdPicture(insertionPicture.getId());
            result.setIdProduct(insertionProduct.getId());
            dao.insertResults(result);
        }
    }

    private VbfWebserviceHandler productsHandler = new VbfWebserviceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok, long timeStamp, String product) {
            if (ok) {
                insertionTimestamp=timeStamp;
                if (product.equals("brick")) {
                    bricks = (List<Brick>) result;
                    saveEachProductPhoto();
                    returnSimilarBricks();
                } else {
                    roofTiles = (List<RoofTile>) result;
                    saveEachProductPhoto();
                    returnSimilarRoofTiles();
                }
            } else {
                String msg = "";
                switch ((String) result) {
                    case "failure":
                        msg = "There has been a problem with your request.";
                        break;
                    case "empty":
                        msg = "No similar products found.";
                        break;
                    case "corrupted":
                        msg = "There is something wrong with the picture you've sent us.";
                        break;
                }
                caller.cantReceiveProducts(msg);
            }
        }
    };

    private void saveEachProductPhoto() {
        if(bricks==null)
            for (RoofTile roofTile: roofTiles) {
                imageDownload(roofTile.getWebsiteImageUrl());
                roofTile.setLocalImageUrl(changeProductDirectory(roofTile.getWebsiteImageUrl()));
            }
        else
            for (Brick brick: bricks) {
                imageDownload(brick.getWebsiteImageUrl());
                brick.setLocalImageUrl(changeProductDirectory(brick.getWebsiteImageUrl()));
            }
    }


    private void imageDownload(String image) {
        Target target =getTarget(image);
        Picasso.get().load(image).into(target);
    }

    private Target getTarget(String image) {
        return new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String[] imageName = image.split("/");
                File file = new File(caller.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+"/"+imageName[imageName.length - 1]);
                if(!file.exists()){
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                        ostream.flush();
                        ostream.close();
                    } catch (IOException e) {
                        Log.e("IOException", e.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


    }

    private String changeProductDirectory(String image) {
        String[] imageName = image.split("/");
        return caller.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+"/"+imageName[imageName.length - 1];

    }

}
