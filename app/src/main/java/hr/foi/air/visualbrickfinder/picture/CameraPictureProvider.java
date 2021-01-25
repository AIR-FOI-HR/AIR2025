package hr.foi.air.visualbrickfinder.picture;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraPictureProvider implements PictureProvider {
    private Uri imageUri;
    private PictureRequester requester;

    @Override
    public void getPicture(PictureRequester requester, int requestCode) {
        this.requester = requester;
        File imageFile = createImageFile();
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(((Fragment)requester).getActivity(), ((Fragment)requester).getActivity().getPackageName(), imageFile);
            requester.onRequestCompleted(imageUri);
            ((Fragment)requester).startActivityForResult(getStartIntent(imageUri), 100);
        }

    }

    private static Intent getStartIntent(Uri imageUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

    private File createImageFile() {
        File directory = ((Fragment)requester).getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageName = setImageName();
        return new File(directory, imageName);
    }

    private String setImageName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH);
        String timestamp = sdf.format(new Date());
        return "VisualBrickFinderImage" + timestamp + ".jpg";
    }
}
