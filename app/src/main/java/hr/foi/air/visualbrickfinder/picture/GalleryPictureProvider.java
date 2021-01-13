package hr.foi.air.visualbrickfinder.picture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import hr.foi.air.visualbrickfinder.MainActivity;

public class GalleryPictureProvider implements PictureProvider {

    @Override
    public void getPicture(PictureRequester requester, int requestCode) {
        ((Fragment)requester).startActivityForResult(getGalleryIntent(), requestCode);
    }

    public static Intent getGalleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        return gallery;
    }

}
