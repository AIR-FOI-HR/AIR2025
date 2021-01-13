package hr.foi.air.visualbrickfinder.picture;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public interface PictureProvider {

     void getPicture(PictureRequester requester, int requestCode);

}
