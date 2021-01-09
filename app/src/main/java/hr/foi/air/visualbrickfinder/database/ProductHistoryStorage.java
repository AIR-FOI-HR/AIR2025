package hr.foi.air.visualbrickfinder.database;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.database.DAO;
import hr.foi.air.database.MyDatabase;
import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.visualbrickfinder.HistoryFragment;
import hr.foi.air.visualbrickfinder.HistoryProductsFragment;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class ProductHistoryStorage {

    private HistoryFragment pictureCaller;
    private HistoryProductsFragment productCaller;
    private  static DAO dao;

    public void getPictures(HistoryFragment caller) {
        this.pictureCaller=caller;
        dao = MyDatabase.getInstance(pictureCaller.getContext()).getDAO();
        returnPictures(dao.loadAllPictures());
    }

    public void getProductsForId(HistoryProductsFragment caller, int id) {
        this.productCaller=caller;
        dao = MyDatabase.getInstance(productCaller.getContext()).getDAO();
        returnProducts(dao.loadRelevantProducts(id));
    }

    private void returnPictures(List<Picture> pictures) {
        pictureCaller.receivePictures(pictures);
    }

    private void returnProducts(List<Product> products) {
        Product firstProduct = products.get(0);
        if(firstProduct.getDimensions()==null){
            List<Brick> bricks = new ArrayList<>();
            for (Product product:products) {
                Brick newBrick = new Brick(
                        product.getProductName(),
                        product.getBrand(),
                        product.getDescription(),
                        null,
                        product.getId());
                newBrick.setLocalImageUrl(product.getProductImage());
                newBrick.setWebsiteImageUrl(product.getProductWebsiteImage());
                bricks.add(newBrick);
            }
            productCaller.receiveProductsBricks(bricks); }
        else {
            List<RoofTile> roofTiles = new ArrayList<>();
            for (Product product : products) {
                RoofTile newRoofTile = new RoofTile(
                        product.getProductName(),
                        product.getBrand(),
                        product.getDescription(),
                        null,
                        product.getDimensions(),
                        product.getId());
                newRoofTile.setLocalImageUrl(product.getProductImage());
                newRoofTile.setWebsiteImageUrl(product.getProductWebsiteImage());
                roofTiles.add(newRoofTile);
            }
            productCaller.receiveProductsRoofTiles(roofTiles);
        }
    }
}
