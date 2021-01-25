package hr.foi.air.visualbrickfinder.picture;


import android.content.Intent;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;


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
