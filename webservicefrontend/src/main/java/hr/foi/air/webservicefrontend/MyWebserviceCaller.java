package hr.foi.air.webservicefrontend;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**@Alen Šobak
 * Change baseUrl to match the API one.
 * Emulator has a gateway 10.0.2.2 instead of localhost
 * 10.0.2.2 on emulator equals 127.0.0.1 on server
 * */

public class MyWebserviceCaller {
    private final String baseUrl = "http://10.0.2.2:9237";
    Retrofit retrofit;

    public MyWebserviceCaller() {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getResponse(Context context, Uri uri){

        MyWebservice serviceApi = retrofit.create(MyWebservice.class);
        //Change Void to response type
        Call<Void> call = serviceApi.createPost(new PostRequestObject(B64Converter.Convert(uri)));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    switch (response.code()){
                        case(400):
                            Toast.makeText(context, "Bad photograph URI", Toast.LENGTH_SHORT).show();
                        break;
                        case(422):
                            Toast.makeText(context, "Photograph B64 corrupted", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                else  Toast.makeText(context,  "Image sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Connection not established", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
