
package hr.foi.air.webservicefrontend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class B64Converter {

    public static String Convert(Uri resultURI) {
        Bitmap bitMap = BitmapFactory.decodeFile(resultURI.getPath());
        if(bitMap!=null){
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayStream);
            byte[] b = byteArrayStream.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        }
        else return "";

    }
}

