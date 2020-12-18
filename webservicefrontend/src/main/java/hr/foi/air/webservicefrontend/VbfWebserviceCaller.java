package hr.foi.air.webservicefrontend;


import android.net.Uri;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;
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
    private final String baseUrl = "http://10.0.2.2:9773";
    Retrofit retrofit;
    private final VbfWebserviceHandler vbfWebserviceHandler;

    public VbfWebserviceCaller(VbfWebserviceHandler vbfWebserviceHandler) {

        this.vbfWebserviceHandler = vbfWebserviceHandler;

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getAllSimilarProducts(Uri uri) {

        VbfWebservice serviceApi = retrofit.create(VbfWebservice.class);
        Call<VbfWebserviceResponse> call = serviceApi.getProducts(new PostRequestObject(B64Converter.Convert(uri)));
        call.enqueue(new Callback<VbfWebserviceResponse>() {
            @Override
            public void onResponse(Call<VbfWebserviceResponse> call, Response<VbfWebserviceResponse> response) {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case (400):
                            handleFailure("uri");
                            break;
                        case (422):
                            handleFailure("corrupted");
                            break;
                    }
                } else {
                    handleResponse(response);
                }
            }

            @Override
            public void onFailure(Call<VbfWebserviceResponse> call, Throwable t) {
                setMockBrickData();
                //setMockRoofTileData();
                //handleFailure("failure");
            }
        });
    }

    private void setMockBrickData() {
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

    private void handleResponse(Response<VbfWebserviceResponse> response) {
        Gson gson = new Gson();
        if (response.body().getItems().contains("dimensions")) {
            RoofTile[] roofTiles = gson.fromJson(
                    response.body().getItems(),
                    RoofTile[].class
            );
            roofTileResponse(roofTiles, response.body().getTimeStamp());
        } else {
            Brick[] bricks = gson.fromJson(
                    response.body().getItems(),
                    Brick[].class
            );
            brickResponse(bricks, response.body().getTimeStamp());
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
