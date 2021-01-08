package hr.foi.air.visualbrickfinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.visualbrickfinder.database.ProductHistoryStorage;
import hr.foi.air.visualbrickfinder.recyclerviews.SimilarItemsAdapter;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;


public class HistoryProductsFragment extends Fragment {

    @BindView(R.id.products_recyclerview)
    RecyclerView recyclerViewProducts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_products, container, false);
        ButterKnife.bind(this, v);
        getProducts();
        return v;
    }

    private void getProducts() {
        MainActivity mainActivity = (MainActivity) getActivity();
        ProductHistoryStorage storage = new ProductHistoryStorage();
        storage.getProductsForId(this, mainActivity.id);
    }


    public void receiveProductsBricks(List<Brick> bricks) {
        recyclerViewProducts.setAdapter(new SimilarItemsAdapter(bricks, null, null));
    }
    public void receiveProductsRoofTiles(List<RoofTile> roofTiles) {
        recyclerViewProducts.setAdapter(new SimilarItemsAdapter(null, roofTiles, null));
    }
}