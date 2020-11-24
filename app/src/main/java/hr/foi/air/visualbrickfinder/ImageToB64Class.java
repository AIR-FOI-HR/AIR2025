package hr.foi.air.visualbrickfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageToB64Class {

    public static String Convert(Uri resultURI) {
        Bitmap bitMap = BitmapFactory.decodeFile(resultURI.getPath());
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayStream);
        byte[] b = byteArrayStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}
