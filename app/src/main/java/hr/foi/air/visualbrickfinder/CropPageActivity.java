package hr.foi.air.visualbrickfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CropPageActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.buttonCropAgain)
    Button btnCropAgain;

    Uri imageURI=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_page);
        ButterKnife.bind(this);

        Bundle extras = this.getIntent().getExtras();
        String imgURI = extras.getString("imgURI");
        imageURI=Uri.parse(imgURI);
        CropImage.activity(imageURI).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
        btnCropAgain.setOnClickListener(v -> onCropAgainClick());

    }

    private void onCropAgainClick() {
        CropImage.activity(imageURI).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
            else if (resultCode == 0) {
                Intent homepageIntent = new Intent(this, MainActivity.class);
                startActivity(homepageIntent);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homepageIntent = new Intent(this, MainActivity.class);
        startActivity(homepageIntent);
    }
}