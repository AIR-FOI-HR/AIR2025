package hr.foi.air.visualbrickfinder;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.visualbrickfinder.picture.CameraPictureProvider;
import hr.foi.air.visualbrickfinder.picture.PictureProvider;
import hr.foi.air.visualbrickfinder.picture.PictureRequester;

public class HomepageFragment extends Fragment implements PictureRequester {

    @BindView(R.id.activity_main_rellay_pulse)
    RelativeLayout pulse;
    @BindView(R.id.activity_main_imgbtn_take_photo)
    ImageButton btnTakePhoto;
    @BindView(R.id.activity_main_txt_tap)
    TextView txtTap;

    private Uri imageUri;

    private PictureProvider pictureProvider;

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
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, v);
        setButtonAnimation(true);

        /**@Alen Šobak
         * Ignores URI exposure
         * */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pictureProvider = new CameraPictureProvider();
        return v;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTakePhoto.setOnClickListener(v -> setCameraAnimation());
    }

    /**
     * @Matej Stojanović
     * Used for handling camera response
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == -1)
                    ((MainActivity) getActivity()).goToCropPageActivity(imageUri);
                else if (resultCode == 0) {
                    setButtonAnimation(true);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_shrink_anim);
                    animation.setFillAfter(true);
                    btnTakePhoto.startAnimation(animation);
                    btnTakePhoto.setImageResource(R.drawable.logo);
                }break;
        }
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
     * @Alen Sanković
     * Synchronizes animation with the speed of opening up camera
     */




    private void setCameraAnimation() {
        if (((MainActivity) getActivity()).requestCameraAndStoragePermission()) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_grow_anim);
            btnTakePhoto.setImageResource(R.color.colorTransparent);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    setButtonAnimation(false);
                }

                /**
                 * @Alen Šobak
                 * Opens camera and saves image in VBF Pictures
                 */
                @Override
                public void onAnimationEnd(Animation animation) {
                    pictureProvider.getPicture(HomepageFragment.this, 100);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    //
                }

            });
            animation.setFillAfter(true);
            btnTakePhoto.startAnimation(animation);
        }
    }


    @Override
    public void onRequestCompleted(Uri pictureUri) {
        imageUri = pictureUri;
    }
}