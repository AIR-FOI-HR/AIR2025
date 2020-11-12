package hr.foi.air.visualbrickfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        btnTakePhoto.setOnClickListener(v -> setCameraAnimation());
    }

    /**
     * Synchronizes animation with the speed of opening up camera
     */

    private void setCameraAnimation() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_grow_anim);
        btnTakePhoto.setImageResource(R.color.colorTransparent);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //početak animacije
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                startActivityForResult(intent, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //
            }
        });
        animation.setFillAfter(true);
        btnTakePhoto.startAnimation(animation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_shrink_anim);
            animation.setFillAfter(true);
            btnTakePhoto.startAnimation(animation);
            btnTakePhoto.setImageResource(R.drawable.logo);
        }
    }


}