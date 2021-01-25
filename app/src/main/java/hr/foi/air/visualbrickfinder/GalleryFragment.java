package hr.foi.air.visualbrickfinder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import hr.foi.air.visualbrickfinder.picture.GalleryPictureProvider;
import hr.foi.air.visualbrickfinder.picture.PictureProvider;
import hr.foi.air.visualbrickfinder.picture.PictureRequester;


public class GalleryFragment extends Fragment implements PictureRequester {
    private static final int PICK_IMAGE = 100;
    private PictureProvider pictureProvider;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        pictureProvider = new GalleryPictureProvider();
        pictureProvider.getPicture(this, PICK_IMAGE);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (resultCode == Activity.RESULT_OK){
                mainActivity.imageUri = getRealPathFromGalleryUri(mainActivity,data.getData());
                mainActivity.goToCropPageActivity(mainActivity.imageUri);
            }
            NavController controller = Navigation.findNavController(getView());
            controller.popBackStack();
        }
    }

    private Uri getRealPathFromGalleryUri(Activity activity, Uri contentURI) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        File file = new File(result);
        if(file.exists()) Log.d("TAG", "postoji: ");
        return Uri.parse(Uri.fromFile(file).toString());
    }

    @Override
    public void onRequestCompleted(Uri pictureUri) {
        //empty for now
    }
}