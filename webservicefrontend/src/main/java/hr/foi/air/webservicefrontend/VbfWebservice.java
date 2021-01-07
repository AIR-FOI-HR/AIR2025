package hr.foi.air.webservicefrontend;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VbfWebservice {
    @POST("/api/Products")
    Call<ResponseBody> getProducts(@Body PostRequestObject postRequestObject);

}
