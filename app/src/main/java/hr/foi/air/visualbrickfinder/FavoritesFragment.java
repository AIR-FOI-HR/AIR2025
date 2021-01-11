package hr.foi.air.visualbrickfinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.database.MyDatabase;
import hr.foi.air.database.data.MockData;
import hr.foi.air.visualbrickfinder.database.ProductHistoryStorage;
import hr.foi.air.visualbrickfinder.recyclerviews.SimilarItemsAdapter;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

//import static hr.foi.air.database.MyDatabase.getInstance;

public class FavoritesFragment extends Fragment {

    //@BindView(R.id.test_button)
    //Button testButton;

    @BindView(R.id.products_recyclerview)
    RecyclerView recyclerViewProducts;

    //public static MyDatabase database;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, v);
        //database = getInstance(this);
        getProducts();


        /*testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mockData();
            }
        });*/
        return v;
    }

    private void getProducts() {
        MainActivity mainActivity = (MainActivity) getActivity();
        ProductHistoryStorage storage = new ProductHistoryStorage();

        storage.getAllFavoritesProducts(this);
    }

    public void receiveProductsBricks(List<Brick> bricks) {

        recyclerViewProducts.setAdapter(new SimilarItemsAdapter(bricks, null, null, this));
    }
    public void receiveProductsRoofTiles(List<RoofTile> roofTiles) {
        recyclerViewProducts.setAdapter(new SimilarItemsAdapter(null, roofTiles, null, this));
    }

    private void mockData() {
        MockData.writeAll(this.getContext());
    }
}