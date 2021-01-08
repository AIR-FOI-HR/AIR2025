package hr.foi.air.visualbrickfinder.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
        returnPictures(dao.loadAllPictures()); //Implement when database is finished, returns all photographs
        //setMockPictureData();
    }

    public void getProductsForId(HistoryProductsFragment caller, int id) {
        this.productCaller=caller;
        dao = MyDatabase.getInstance(productCaller.getContext()).getDAO();
        returnProducts(dao.loadRelevantProducts(id));
        //loadData(id); //Implement when database is finished, returns all photographs
        //setMockProductData(id);
    }



  /*  private void setMockPictureData() {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        List<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture(
                "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxleHBsb3JlLWZlZWR8MXx8fGVufDB8fHw%3D&w=1000&q=80",
                date,
                1));
        pictures.add(new Picture(
                "https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                date,
                2));
        pictures.add(new Picture(
                "https://images.ctfassets.net/79nimht05j33/5XrYmDVAa2z7zGJZNVZPX1/91e8c3419148292d88a36ca34b2abdb5/JamesHardieSiding-33.jpg?w=1500&q=70",
                date,
                3));

        returnPictures(pictures);
    }


    private void setMockProductData(int id) {
        List<Brick> bricks = new ArrayList<>();
        switch (id){
            case 1:
                bricks.add(new Brick(
                        "Ambiente Vulkangrau siva - rustikalna sa šupljinama",
                        "Terca",
                        "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                        "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Ambiente_Vulkangrau.jpg",
                        2312));
                returnProducts(bricks);
                break;

            case 2:
                bricks.add(new Brick(
                    "Calau smede crvena -  modrno sivi podton - glatka sa supljinama",
                    "Terca",
                    "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                    "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Calau.jpg",
                    4222));

                bricks.add(new Brick(
                        "Rugen sareno zuta - glatka puna",
                        "Terca",
                        "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                        "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Ruegen.jpg",
                        4233));
                returnProducts(bricks);
                break;

            case 3:
                bricks.add(new Brick(
                        "Calau smede crvena -  modrno sivi podton - glatka sa supljinama",
                        "Terca",
                        "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                        "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Calau.jpg",
                        4222));

                bricks.add(new Brick(
                        "Rugen sareno zuta - glatka puna",
                        "Terca",
                        "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                        "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Ruegen.jpg",
                        4233));

                bricks.add(new Brick(
                        "Heide crveno nijansirana - glatka puna",
                        "Terca",
                        "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                        "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Heide.jpg",
                        1234));
                returnProducts(bricks);
                break;

        }
    }
*/

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
                roofTiles.add(newRoofTile);
            }
            productCaller.receiveProductsRoofTiles(roofTiles);
        }
    }
}
