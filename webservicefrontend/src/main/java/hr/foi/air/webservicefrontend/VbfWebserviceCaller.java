package hr.foi.air.webservicefrontend;


import android.net.Uri;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @Alen Šobak
 * Change baseUrl to match the API one.
 * Emulator has a gateway 10.0.2.2 instead of localhost
 * 10.0.2.2 on emulator equals 127.0.0.1 on server
 */

public class VbfWebserviceCaller {
    private final String baseUrl = "http://10.0.2.2:9237";
    Retrofit retrofit;
    private final VbfWebserviceHandler vbfWebserviceHandler;

    public VbfWebserviceCaller(VbfWebserviceHandler vbfWebserviceHandler) {

        this.vbfWebserviceHandler = vbfWebserviceHandler;

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getAllSimilarProducts(Uri uri) {
        VbfWebservice serviceApi = retrofit.create(VbfWebservice.class);
        Call<ResponseBody> call = serviceApi.getProducts(new PostRequestObject(B64Converter.Convert(uri)));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case (400):
                            handleFailure("failure");
                            break;
                        case (422):
                            handleFailure("corrupted");
                            break;
                    }
                } else {
                    switch (response.code()) {
                        case (200):
                            try {
                                handleResponse(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case (204):
                            handleFailure("empty");
                            break;
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //setMockBrickData();
                //setMockRoofTileData();
                handleFailure("failure");
            }
        });
    }

   /* private void setMockBrickData() {
        List<Brick> bricks = new ArrayList<>();
        bricks.add(new Brick(
                "Ambiente Vulkangrau siva - rustikalna sa šupljinama",
                "Terca",
                "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Ambiente_Vulkangrau.jpg"));
        bricks.add(new Brick(
                "Calau smede crvena -  modrno sivi podton - glatka sa supljinama",
                "Terca",
                "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Calau.jpg"));

        bricks.add(new Brick(
                "Rugen sareno zuta - glatka puna",
                "Terca",
                "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Ruegen.jpg"));

        bricks.add(new Brick(
                "Heide crveno nijansirana - glatka puna",
                "Terca",
                "Terca klinker fasadna opeka pruža zaštitni sloj od vremenskih utjecaja i nudi iznimnu kreativnu slobodu pri oblikovanju fasade. Budući da je svaka opeka indi\u00ADvidualna stvara bezvremenski lijepe objekte s nezamjenjivim karakterom i čarom.",
                "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/germany/marketing/photography/productshots/facade/product-texture/DE_MKT_PSH_TER_Heide.jpg"));

        vbfWebserviceHandler.onDataArrived(
                bricks,
                true,
                1234,
                "brick"
        );

    }

    private void setMockRoofTileData() {
        List<RoofTile> roofTiles = new ArrayList<>();
        roofTiles.add(new RoofTile(
                "Saturn Natur Color crvena",
                "Tondach",
                "Saturn je crijep velikog formata iz moderne linije crijepa koji zadovoljava najnovije zahtjeve suvremene arhitekture. Jednostavnim oblikom savršeno se uklapa u moderne, pravokutne strukture. Njegova reducirana površina omogućuje Vam stvaranje pravocrtnih linija na krovu. Postavlja se brzo i jednostavno te ima mogućnost kliznog letvanja. Kvalitetan i vizualno lijep crijep krovu daje individualnost i estetiku. Tondach Saturn raspoloživ je u nekoliko engobiranih i glaziranih varijanti. Oplemenjena gornja površina pridonosi postojanosti boja te dugotrajnoj kvaliteti krova prema Vašim željama.",
                "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/croatia/marketing/photography/productshots/roof/single-product/HR_MKT_PHO_PSH_ROF_TON_Saturn_Natur_Color_Crvena_web_01.jpg",
                "48,5x29"));

        roofTiles.add(new RoofTile(
                "Figaro Natur Color bakrenosmeđa",
                "Tondach",
                "Figaro je moderan crijep, koji daje odgovor suvremenim zahtjevima arhitekture. Svojom jednostavnošću podržava pravokutne strukture krovova. Kreativnost ne poznaje granice. S različitim oblicima i bojama, mogu se stvoriti zanimljivi efekti. S lakoćom su izvodivi nekonvencionalni oblici krovova.",
                "https://foiwbstorage.blob.core.windows.net/foi-wb/wb-products/wb-products/content/dam/wienerberger/croatia/marketing/photography/productshots/roof/single-product/HR_MKT_PHO_PSH_ROF_TON_Figaro_Natur_Color_Bakreno_Smeda_web_01.jpg",
                "45,2x26,5"));


        vbfWebserviceHandler.onDataArrived(
                roofTiles,
                true,
                1234,
                "roofTile"
        );

    }
*/
    private void handleResponse(String response) throws IOException {

        long tsLong = System.currentTimeMillis()/1000;
        Gson gson = new Gson();

        if(response.startsWith("[") && response.contains("primaId") ){
            if (response.contains("dimensions")) {
                RoofTile[] roofTiles = gson.fromJson(
                        response,
                        RoofTile[].class
                );
                roofTileResponse(roofTiles, tsLong);
            } else {
                Brick[] bricks = gson.fromJson(
                        response,
                        Brick[].class
                );
                brickResponse(bricks,tsLong);
            }
        }
        else{
           handleFailure("empty");
        }
    }

    private void brickResponse(Brick[] bricks, long timestamp) {
        vbfWebserviceHandler.onDataArrived(
                Arrays.asList(bricks),
                true,
                timestamp,
                "brick"
        );
    }

    private void roofTileResponse(RoofTile[] roofTiles, long timestamp) {
        vbfWebserviceHandler.onDataArrived(
                Arrays.asList(roofTiles),
                true,
                timestamp,
                "roofTile"
        );
    }

    private void handleFailure(String reason) {
        vbfWebserviceHandler.onDataArrived(
                reason,
                false,
                1234,
                null
        );
    }
}
