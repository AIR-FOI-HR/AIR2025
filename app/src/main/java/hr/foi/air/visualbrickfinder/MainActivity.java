package hr.foi.air.visualbrickfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_view_btmnav)
    BottomNavigationView btmNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Removing status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpNavigation();

    }

    public void Prijelaz(Uri imageUri) {
        Intent homepageIntent = new Intent(this, CropPageActivity.class);
        homepageIntent.putExtra("imgURI",imageUri.toString());
        startActivity(homepageIntent);
    }


    /**
     * @Alen Sanković
     * Sets Android Navigation and connects it to bottom navigation
     */
    public void setUpNavigation(){
        NavHostFragment navHostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(btmNav, navHostFragment.getNavController());
    }
}