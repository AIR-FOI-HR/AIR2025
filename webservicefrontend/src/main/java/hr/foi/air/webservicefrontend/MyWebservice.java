package hr.foi.air.webservicefrontend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyWebservice {
    @POST("api/Products")
    Call<Void> createPost(@Body PostRequestObject postRequestObject);

}
