package hr.foi.air.visualbrickfinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.visualbrickfinder.database.Picture;
import hr.foi.air.visualbrickfinder.database.ProductHistoryStorage;
import hr.foi.air.visualbrickfinder.recyclerviews.SimilarItemsAdapter;
import hr.foi.air.visualbrickfinder.webservice.SimilarProductsStorage;


public class HistoryFragment extends Fragment {

    @BindView(R.id.pictures_recyclerview)
    RecyclerView recyclerViewPictures;
    private int pictureId;


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, v);
        getPicturesFromDatabase();
        return v;
    }

    private void getPicturesFromDatabase() {
        ProductHistoryStorage storage = new ProductHistoryStorage();
        storage.getPictures(this);
    }


    public void receivePictures(List<Picture> pictures) {
        recyclerViewPictures.setAdapter(new SimilarItemsAdapter(null, null, pictures, this));
    }

    public void getProductsWithId(int id){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.switchToHistoryProductsFragment(id);
    }


}