package hr.foi.air.visualbrickfinder;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setButtonAnimation();
    }

    /**
     * Sets pulsing animation on main button
     */
    private void setButtonAnimation() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.btn_glow_anim);
        pulse.startAnimation(animation);
    }


}