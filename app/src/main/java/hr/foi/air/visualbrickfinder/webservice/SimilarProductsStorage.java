package hr.foi.air.visualbrickfinder.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.util.List;

import hr.foi.air.visualbrickfinder.SimilarProductsFragment;
import hr.foi.air.webservicefrontend.VbfWebserviceCaller;
import hr.foi.air.webservicefrontend.VbfWebserviceHandler;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class SimilarProductsStorage {

    private List<Brick> bricks;
    private List<RoofTile> roofTiles;
    private SimilarProductsFragment caller;

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
        //saveProductImages();
    }

    private void returnSimilarBricks() {
        //Here you can handle updating local database later on
        //Merge this and returnSimilarRoofTiles() in one if needed
        caller.receiveBricks(bricks);
        //saveProductImages();
    }

    private VbfWebserviceHandler productsHandler = new VbfWebserviceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok, long timeStamp, String product) {
            if (ok) {
                if (product.equals("brick")) {
                    bricks = (List<Brick>) result;
                    returnSimilarBricks();
                } else {
                    roofTiles = (List<RoofTile>) result;
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

}
