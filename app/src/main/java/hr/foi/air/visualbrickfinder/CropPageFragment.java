package hr.foi.air.visualbrickfinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CropPageFragment extends Fragment {
    @BindView(R.id.imageView)
    ImageView imageView;


    public CropPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crop_page, container, false);
        ButterKnife.bind(this,v);
        Picasso.get().load(getArguments().getString("imgURI")).into(imageView);
        return v;

    }
}