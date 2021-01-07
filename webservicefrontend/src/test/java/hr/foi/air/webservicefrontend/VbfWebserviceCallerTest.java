package hr.foi.air.webservicefrontend;

import android.util.Log;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class VbfWebserviceCallerTest {

    MockWebServer mockWebServer;
    Response<VbfWebserviceResponse> response;
    String reason;
    int timestamp;
    List<Brick> bricks;
    List<RoofTile> roofTiles;

    @Before
    public void setUp() throws IOException {
        String responseJson = "[\n" +
                "  {\n" +
                "    \"name\": \"First brick\",\n" +
                "    \"brand\": \"First brand\",\n" +
                "    \"description\": \"First description\",\n" +
                "    \"url\" : \"First url\",\n" +
                "    \"image\" : \"First image\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Second brick\",\n" +
                "    \"brand\": \"Second brand\",\n" +
                "    \"description\": \"Second description\",\n" +
                "    \"url\" : \"Second url\",\n" +
                "    \"image\" : \"Second image\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Third brick\",\n" +
                "    \"brand\": \"Third brand\",\n" +
                "    \"description\": \"Third description\",\n" +
                "    \"url\" : \"Third url\",\n" +
                "    \"image\" : \"Third image\"\n" +
                "  }\n" +
                "]";

        VbfWebserviceResponse responseObject = new VbfWebserviceResponse();
        responseObject.setItems(responseJson);
        responseObject.setTimeStamp(12032020);

        response = retrofit2.Response.success(responseObject);

        reason = "corrupted";
        timestamp = 123456;

        bricks = new ArrayList<>();
        bricks.add(new Brick("Prva cigla", "Prvi brand", "dsajkbdsaldbashkdbslahdbsaldbsjaldnsčjadnsačdnsajdbslad","link", "image"));
        bricks.add(new Brick("Druga cigla", "Druga brand", "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD","link", "image"));
        bricks.add(new Brick("Treca cigla", "Treca brand", "ASSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS","link", "image"));

        roofTiles = new ArrayList<>();
        roofTiles.add(new RoofTile("Prva cigla", "Prvi brand", "dsajkbdsaldbashkdbslahdbsaldbsjaldnsčjadnsačdnsajdbslad","link", "dimensions", "image"));
    }


    @Test
    public void handleResponseForBricks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        if (response.body().getItems().contains("dimensions")){
            RoofTile[] roofTiles = gson.fromJson(
                    response.body().getItems(),
                    RoofTile[].class
            );
            assertEquals(3, roofTiles.length);

        }
        else{
            Brick[] bricks = gson.fromJson(
                    response.body().getItems(),
                    Brick[].class
            );
            assertEquals(3, bricks.length);
        }
    }

    @Test
    public void brickResponse(){
        productsHandler.onDataArrived(
                bricks,
                true,
                timestamp,
                "brick"
        );
    }

    @Test
    public void roofTileResponse(){
        productsHandler.onDataArrived(
                roofTiles,
                true,
                timestamp,
                "roofTile"
        );
    }

    @Test
    public void handleFailure(){
        productsHandler.onDataArrived(
                reason,
                false,
                1234,
                null
        );
    }


    private VbfWebserviceHandler productsHandler = new VbfWebserviceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok, long timeStamp, String product) {
            if (ok){
                if (product.equals("brick")){
                    List<Brick> bricks = (List<Brick>) result;
                    assertEquals(3, bricks.size());
                }
                else{
                    List<RoofTile> roofTiles = (List<RoofTile>) result;
                    assertEquals(1, roofTiles.size());
                }
            }
            else{
                assertEquals(null, product);
            }
        }
    };

}