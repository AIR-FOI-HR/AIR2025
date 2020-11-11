package hr.foi.air.visualbrickfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_rellay_pulse)
    RelativeLayout pulse;
    @BindView(R.id.activity_main_imgbtn_take_photo)
    ImageButton btnTakePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setButtonAnimation();
        requestCameraPermission();
    }

    /**
     * @ Alen Sanković
     * Sets pulsing animation on main button
     */
    private void setButtonAnimation() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_glow_anim);
        pulse.startAnimation(animation);
    }

    /**
     * Matej Stojanović
     * Requests camera permission
     */
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        /**
         * Matej Stojanović
         * Enables camera usage
         */
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCameraAnimation();
                Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                startActivityForResult(intent, 100);

            }
        });
    }

    private void setCameraAnimation() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_grow_anim);
        btnTakePhoto.setImageResource(R.color.colorTransparent);

        btnTakePhoto.startAnimation(animation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            btnTakePhoto.setImageResource(R.drawable.logo);
        }
    }
}