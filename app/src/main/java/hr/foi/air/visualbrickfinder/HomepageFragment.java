package hr.foi.air.visualbrickfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageFragment extends Fragment {

    @BindView(R.id.activity_main_rellay_pulse)
    RelativeLayout pulse;
    @BindView(R.id.activity_main_imgbtn_take_photo)
    ImageButton btnTakePhoto;
    @BindView(R.id.activity_main_txt_tap)
    TextView txtTap;

    public HomepageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this,v);
        setButtonAnimation(true);
        requestCameraPermission();
        return  v;
    }

    /**
     * @ Alen Sanković
     * Sets pulsing animation on main button and hides elements if param is true
     * Else removes animation and shows elements
     */
    private void setButtonAnimation(boolean startAnime) {
        if (startAnime) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_glow_anim);
            txtTap.setVisibility(View.VISIBLE);
            pulse.setVisibility(View.VISIBLE);
            pulse.startAnimation(animation);
        } else {
            txtTap.setVisibility(View.GONE);
            pulse.setVisibility(View.GONE);
            pulse.clearAnimation();
        }
    }

    /**
     * @Matej Stojanović
     * Requests camera permission
     */
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        /**
         * @Matej Stojanović
         * Enables camera usage
         */
        btnTakePhoto.setOnClickListener(v -> setCameraAnimation());
    }

    /**
     * @Alen Sanković
     * Synchronizes animation with the speed of opening up camera
     */

    private void setCameraAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_grow_anim);
        btnTakePhoto.setImageResource(R.color.colorTransparent);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setButtonAnimation(false);
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

    /**
     * @Matej Stojanović
     * Used for handling camera response
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            setButtonAnimation(true);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_shrink_anim);
            animation.setFillAfter(true);
            btnTakePhoto.startAnimation(animation);
            btnTakePhoto.setImageResource(R.drawable.logo);
        }
    }
}