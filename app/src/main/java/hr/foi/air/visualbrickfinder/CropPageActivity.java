package hr.foi.air.visualbrickfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CropPageActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.buttonCropAgain)
    ImageButton btnCropAgain;

    @BindView(R.id.buttonAcceptCropped)
    ImageButton btnAcceptCrop;

    Uri imageURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_page);
        ButterKnife.bind(this);

        Bundle extras = this.getIntent().getExtras();
        String imgURI = extras.getString("imgURI");
        imageURI = Uri.parse(imgURI);
        CropImage.activity(imageURI).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);

    }

    private void onAcceptCrop(Uri resultURI) {
        String encodedImage = croppedImageToB64(resultURI);
        Intent homepageIntent = new Intent(this, MainActivity.class);
        startActivity(homepageIntent);
    }

    private void onCropAgainClick() {
        CropImage.activity(imageURI).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

    /**
     * @Alen Šobak
     */
    private String croppedImageToB64(Uri resultURI) {
        Bitmap bm = BitmapFactory.decodeFile(resultURI.getPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    /**
     * @Alen Šobak
     * result code 0 is returned on backing out of crop screen
     */
    @SuppressWarnings("ThrowableNotThrown")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
                btnAcceptCrop.setOnClickListener(v -> onAcceptCrop(resultUri));
                btnCropAgain.setOnClickListener(v -> onCropAgainClick());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
            } else if (resultCode == 0) {
                deleteImageFromStorage();
                Intent homepageIntent = new Intent(this, MainActivity.class);
                startActivity(homepageIntent);
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteImageFromStorage();
        Intent homepageIntent = new Intent(this, MainActivity.class);
        startActivity(homepageIntent);
    }

    public void deleteImageFromStorage() {
        String[] imageName = imageURI.toString().split("/");
        File directory = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + imageName[imageName.length - 1]);
        if (directory.exists()) {
            if (directory.delete()) {
                Log.d("MyApp", "file Deleted :" + imageURI.getPath());
            } else {
                Log.d("MyApp", "file not Deleted :" + imageURI.getPath());
            }
        } else
            Log.d("MyApp", "nePostoji");
    }
}