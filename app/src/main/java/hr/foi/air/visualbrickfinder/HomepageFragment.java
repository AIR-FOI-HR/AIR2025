package hr.foi.air.visualbrickfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageFragment extends Fragment {

    @BindView(R.id.activity_main_rellay_pulse)
    RelativeLayout pulse;
    @BindView(R.id.activity_main_imgbtn_take_photo)
    ImageButton btnTakePhoto;
    @BindView(R.id.activity_main_txt_tap)
    TextView txtTap;

    Uri imageUri;

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
        requestCameraAndStoragePermission();

        /**@Alen Šobak
         * Ignores URI exposure
         * */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        return v;
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
     * @Alen Šobak
     * Reworked to also request storage permission
     */
    private void requestCameraAndStoragePermission() {
        String permissions[] = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (ContextCompat.checkSelfPermission(getActivity(), permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions, 101);
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

            /**@Alen Šobak
             * Opens camera and saves image in VBF gallery
             * */
            @Override
            public void onAnimationEnd(Animation animation) {
                CheckOrCreateGalleryFolder();
                Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/VisualBrickFinder");
                String imageName = setImageName();
                File imageFile = new File(directory, imageName);
                imageUri = Uri.fromFile(imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
    /**@Alen Šobak
     * */
    private String setImageName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "VisualBrickFinderImage" + timestamp + ".jpg";
    }


    /**
     * @Matej Stojanović
     * Used for handling camera response
     * @Alen Šobak
     * On accepting a photo, moves to crop page
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NavController navController = Navigation.findNavController(this.getView());

        if (requestCode == 100 && resultCode == -1) {
            navController.navigate(R.id.cropPageFragment, CreateImageURIBundle());
        } else if (requestCode == 100 && resultCode == 0) {
            setButtonAnimation(true);
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_shrink_anim);
            animation.setFillAfter(true);
            btnTakePhoto.startAnimation(animation);
            btnTakePhoto.setImageResource(R.drawable.logo);
        }
    }

    /**
     * @Alen Šobak
     */
    private Bundle CreateImageURIBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("imgURI", imageUri.toString());
        return bundle;
    }

    /**
     * @Alen Šobak
     * Creates VisualBrickFinder gallery folder if it doesn't exist
     */
    private static void CheckOrCreateGalleryFolder() {
        File folder = new File(getGalleryPath() + File.separator + "VisualBrickFinder");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            //Do when folder is made successfully
        } else {
            // Do something else on failure
        }
    }

    /**
     * @Alen Šobak
     */
    private static String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
    }
}