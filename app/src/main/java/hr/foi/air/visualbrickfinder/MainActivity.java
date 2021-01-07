package hr.foi.air.visualbrickfinder;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Dialog popUpDialog;
    @BindView(R.id.activity_main_view_btmnav)
    BottomNavigationView btmNav;
    public static Activity activity;
    private static final int PICK_IMAGE = 100;
    static final String IMAGE_URI = "IMAGE_URI";

    public Uri imageUri;
    public Uri cropImageUri;
    public int id;
    private NavHostFragment navHostFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Removing status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        popUpDialog=new Dialog(this);
        if(isFirstTimeUsingApp()){
            ShowAndHandlePopUp();
        }
        ButterKnife.bind(this);
        setUpNavigation();
        requestCameraAndStoragePermission();
        if (checkCallingActivity()) switchToProductsFragment();
        activity=this;
    }

    public boolean isFirstTimeUsingApp(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    private void ShowAndHandlePopUp() {
        popUpDialog.setContentView(R.layout.popup_window);
        popUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window= popUpDialog.getWindow();
        WindowManager.LayoutParams popUpParams =window.getAttributes();
        popUpParams.y=5;
        window.setAttributes(popUpParams);
        ((Button) popUpDialog.findViewById(R.id.popup_close)).setOnClickListener(v->closePopUp());
        ((Button) popUpDialog.findViewById(R.id.popup_show_help)).setOnClickListener(v->openHelp());
        popUpDialog.show();
    }

    private void closePopUp() {
        popUpDialog.dismiss();
    }

    private void openHelp(){
        popUpDialog.dismiss();
        navHostFragment.getNavController().navigate(R.id.action_homepageFragment_to_main_menu_item_help);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Allow camera permission", Toast.LENGTH_SHORT).show();
                if (grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[2] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Allow storage permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            goToCropPageActivity(imageUri);
        }
        else if(resultCode == RESULT_CANCELED){
            Log.d("AAAAAAAAA", String.valueOf(navHostFragment.getChildFragmentManager().getBackStackEntryCount()));
        }
    }

    @Override
    public void onBackPressed() {
        if(btmNav.getVisibility() == View.GONE) btmNav.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    public static Intent getCameraIntent(Context context, Uri imageUri) {
        Intent intent = new Intent(context, CropPageActivity.class);
        intent.putExtra(IMAGE_URI, imageUri.toString());
        return intent;
    }




    public void goToCropPageActivity(Uri imageUri) {
        startActivity(getCameraIntent(this, imageUri));
    }


    /**
     * @Alen Sanković
     * Sets Android Navigation and connects it to bottom navigation
     */
    public void setUpNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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

    public void switchToHistoryProductsFragment(int id){
        this.id=id;
        navHostFragment.getNavController().navigate(R.id.historyProductsFragment);
    }

    private boolean checkCallingActivity(){
        return getIntent().getIntExtra("calling-activity", 0) == 1001;
    }

    private void switchToProductsFragment(){
        cropImageUri = Uri.parse(getIntent().getStringExtra("uri"));
        imageUri = Uri.parse(getIntent().getStringExtra("uriPhoto"));
        navHostFragment.getNavController().navigate(R.id.action_homepageFragment_to_similarProductsFragment);
        btmNav.setVisibility(View.GONE);
    }


}