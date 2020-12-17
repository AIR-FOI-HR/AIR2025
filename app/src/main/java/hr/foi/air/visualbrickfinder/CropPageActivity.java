package hr.foi.air.visualbrickfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.webservicefrontend.MyWebserviceCaller;


public class CropPageActivity extends AppCompatActivity {
    @BindView(R.id.image_cropped)
    ImageView imageView;

    @BindView(R.id.btn_crop_again)
    FloatingActionButton btnCropAgain;

    @BindView(R.id.btn_accept_crop)
    FloatingActionButton btnAcceptCrop;

    static final String IMAGE_URI = "IMAGE_URI";

    Uri imageUri = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_page);
        ButterKnife.bind(this);
        getUri();
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
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
                deleteImageFromStorage();
                MainActivity.activity.finish();
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == 0) {
                deleteImageFromStorage();
                MainActivity.activity.finish();
                Intent homepageIntent = new Intent(this, MainActivity.class);
                startActivity(homepageIntent);
                finish();
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteImageFromStorage();
        MainActivity.activity.finish();
        Intent homepageIntent = new Intent(this, MainActivity.class);
        startActivity(homepageIntent);
        finish();
    }


   

    public void deleteImageFromStorage() {
        String[] imageName = imageUri.toString().split("/");
        File directory = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + imageName[imageName.length - 1]);
        if (directory.exists())
            directory.delete();
    }

    private void getUri() {
        Bundle extras = this.getIntent().getExtras();
        String imgURI = extras.getString(IMAGE_URI);
        imageUri = Uri.parse(imgURI);
    }

    private void onAcceptCrop(Uri resultUri) {
        MyWebserviceCaller apiCaller = new MyWebserviceCaller();
        apiCaller.getResponse(this,resultUri);
        MainActivity.activity.finish();
        Intent homepageIntent = new Intent(this, MainActivity.class);
        startActivity(homepageIntent);
        finish();
    }

    private void onCropAgainClick() {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

}