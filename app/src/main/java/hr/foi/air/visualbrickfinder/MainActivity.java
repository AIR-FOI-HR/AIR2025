package hr.foi.air.visualbrickfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.Manifest;
import android.content.Context;
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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_view_btmnav)
    BottomNavigationView btmNav;

    static final String IMAGE_URI = "IMAGE_URI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Removing status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpNavigation();

        requestCameraAndStoragePermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101:
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "Allow camera permission", Toast.LENGTH_SHORT).show();
                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                            grantResults[2] != PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this, "Allow storage permission", Toast.LENGTH_SHORT).show();
                }break;
        }
    }

    public static Intent getStartIntent(Context context, Uri imageUri) {
        Intent intent = new Intent(context, CropPageActivity.class);
        intent.putExtra(IMAGE_URI, imageUri.toString());
        return intent;
    }

    public void goToCropPageActivity(Uri imageUri) {
        startActivity(getStartIntent(this, imageUri));
    }


    /**
     * @Alen Sanković
     * Sets Android Navigation and connects it to bottom navigation
     */
    public void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(btmNav, navHostFragment.getNavController());
    }


    /**
     * @Matej Stojanović
     * Requests camera permission
     * @Alen Šobak
     * Reworked to also request storage permission
     */
    public boolean requestCameraAndStoragePermission() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, permissions[2]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 101);
            return false;
        } else
            return true;
    }

}