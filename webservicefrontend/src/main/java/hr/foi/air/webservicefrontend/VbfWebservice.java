package hr.foi.air.webservicefrontend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VbfWebservice {
    @POST("api/Products")
    Call<VbfWebserviceResponse> getProducts(@Body PostRequestObject postRequestObject);

}
