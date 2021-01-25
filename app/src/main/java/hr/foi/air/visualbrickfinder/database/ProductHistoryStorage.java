package hr.foi.air.visualbrickfinder.database;

import java.util.ArrayList;
import java.util.List;
import hr.foi.air.database.DAO;
import hr.foi.air.database.MyDatabase;
import hr.foi.air.database.entities.Picture;
import hr.foi.air.database.entities.Product;
import hr.foi.air.visualbrickfinder.FavoritesFragment;
import hr.foi.air.visualbrickfinder.HistoryFragment;
import hr.foi.air.visualbrickfinder.HistoryProductsFragment;
import hr.foi.air.visualbrickfinder.SimilarProductsFragment;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class ProductHistoryStorage {

    private  static DAO dao;
    private HistoryFragment pictureCaller;
    private HistoryProductsFragment productCaller;
    private FavoritesFragment productFavoritesCaller;
    private SimilarProductsFragment similarProductsFragmentCaller;


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

    public void getAllFavoritesProducts(FavoritesFragment caller) {
        this.productFavoritesCaller=caller;
        dao = MyDatabase.getInstance(productFavoritesCaller.getContext()).getDAO();
        returnFavoriteProducts(dao.loadAllFavoritesProducts());
    }

    public void setProductAsFavorite(FavoritesFragment caller, String name) {
        this.productFavoritesCaller = caller;
        dao = MyDatabase.getInstance(productFavoritesCaller.getContext()).getDAO();
        dao.setProductAsFavorite(name);
    }

    public void setProductAsNOFavorite(FavoritesFragment caller, String name){
        dao = MyDatabase.getInstance(caller.getContext()).getDAO();
        dao.setProductAsNOFavorite(name);
    }

    public void setProductAsFavorite(HistoryProductsFragment caller, String name) {
        this.productCaller = caller;
        dao = MyDatabase.getInstance(productCaller.getContext()).getDAO();
        dao.setProductAsFavorite(name);
    }

    public void setProductAsNOFavorite(HistoryProductsFragment caller, String name){
        dao = MyDatabase.getInstance(caller.getContext()).getDAO();
        dao.setProductAsNOFavorite(name);
    }

    public void setProductAsFavorite(SimilarProductsFragment caller, String name) {
        this.similarProductsFragmentCaller = caller;
        dao = MyDatabase.getInstance(similarProductsFragmentCaller.getContext()).getDAO();
        dao.setProductAsFavorite(name);
    }

    public void setProductAsNOFavorite(SimilarProductsFragment caller, String name){
        dao = MyDatabase.getInstance(caller.getContext()).getDAO();
        dao.setProductAsNOFavorite(name);
    }

    private void returnPictures(List<Picture> pictures) {
        pictureCaller.receivePictures(pictures);
    }

    private void returnProducts(List<Product> products) {
        if(!products.isEmpty()){
            Product firstProduct = products.get(0);

            if(firstProduct.getDimensions()==null){
                List<Brick> bricks = new ArrayList<>();
                for (Product product:products) {
                    Brick newBrick = new Brick(
                            product.getProductName(),
                            product.getBrand(),
                            product.getDescription(),
                            null,
                            product.getId(),
                            product.getFlagFavorite());
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
                            product.getId(),
                            product.getFlagFavorite());
                    newRoofTile.setLocalImageUrl(product.getProductImage());
                    newRoofTile.setWebsiteImageUrl(product.getProductWebsiteImage());
                    roofTiles.add(newRoofTile);
                }
                productCaller.receiveProductsRoofTiles(roofTiles);
            }
        }

    }

    private void returnFavoriteProducts(List<Product> products) {
        if(!products.isEmpty()){
            Product firstProduct = products.get(0);

            if(firstProduct.getDimensions()==null){
                List<Brick> bricks = new ArrayList<>();
                for (Product product:products) {
                    Brick newBrick = new Brick(
                            product.getProductName(),
                            product.getBrand(),
                            product.getDescription(),
                            null,
                            product.getId(),
                            product.getFlagFavorite());
                    newBrick.setLocalImageUrl(product.getProductImage());
                    newBrick.setWebsiteImageUrl(product.getProductWebsiteImage());
                    bricks.add(newBrick);
                }

                productFavoritesCaller.receiveProductsBricks(bricks);
            }
            else {
                List<RoofTile> roofTiles = new ArrayList<>();
                for (Product product : products) {
                    RoofTile newRoofTile = new RoofTile(
                            product.getProductName(),
                            product.getBrand(),
                            product.getDescription(),
                            null,
                            product.getDimensions(),
                            product.getId(),
                            product.getFlagFavorite());
                    newRoofTile.setLocalImageUrl(product.getProductImage());
                    newRoofTile.setWebsiteImageUrl(product.getProductWebsiteImage());
                    roofTiles.add(newRoofTile);
                }
                productFavoritesCaller.receiveProductsRoofTiles(roofTiles);

            }
        }

    }
}
