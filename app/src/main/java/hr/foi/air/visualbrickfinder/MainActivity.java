package hr.foi.air.visualbrickfinder;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_view_btmnav)
    BottomNavigationView btmNav;

    public static Activity activity;
    private static final int PICK_IMAGE = 100;
    static final String IMAGE_URI = "IMAGE_URI";
    Uri imageUri;



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
        setGalleryListener();
        activity=this;

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

    public static Intent getCameraIntent(Context context, Uri imageUri) {
        Intent intent = new Intent(context, CropPageActivity.class);
        intent.putExtra(IMAGE_URI, imageUri.toString());
        return intent;
    }

    public static Intent getGalleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        return gallery;
    }


    public void goToCropPageActivity(Uri imageUri) {
        startActivity(getCameraIntent(this, imageUri));
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

    private void setGalleryListener() {
        btmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.main_menu_item_gallery){
                    startActivityForResult(getGalleryIntent(), PICK_IMAGE);
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            goToCropPageActivity(imageUri);
        }
    }
}