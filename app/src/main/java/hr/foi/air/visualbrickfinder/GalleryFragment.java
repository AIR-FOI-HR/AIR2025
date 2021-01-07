package hr.foi.air.visualbrickfinder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GalleryFragment extends Fragment {


    public GalleryFragment() {
        // Required empty public constructor
    }

    private static final int PICK_IMAGE = 100;

    public static Intent getGalleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        return gallery;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        startActivityForResult(getGalleryIntent(), PICK_IMAGE);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (resultCode == Activity.RESULT_OK){
                mainActivity.imageUri = data.getData();
                mainActivity.goToCropPageActivity(mainActivity.imageUri);
            }
            NavController controller = Navigation.findNavController(getView());
            controller.popBackStack();
        }
    }
}