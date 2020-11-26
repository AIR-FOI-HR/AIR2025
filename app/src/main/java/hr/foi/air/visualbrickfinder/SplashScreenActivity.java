package hr.foi.air.visualbrickfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Removing status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent mainActivityIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            overridePendingTransition(R.anim.mainactivity_fadein_anim,R.anim.splashscreen_fadeout_anim);
            SplashScreenActivity.this.finish();
        }, 1700);
    }

}