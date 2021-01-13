package hr.foi.air.visualbrickfinder.picture;

import android.net.Uri;

public interface PictureRequester {
    void onRequestCompleted(Uri pictureUri);
}
