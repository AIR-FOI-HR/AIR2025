package hr.foi.air.visualbrickfinder;


import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.visualbrickfinder.recyclerviews.SimilarItemsAdapter;
import hr.foi.air.visualbrickfinder.webservice.SimilarProductsStorage;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class SimilarProductsFragment extends Fragment {

    @BindView(R.id.products_recyclerview)
    RecyclerView recyclerViewProducts;

    @BindView(R.id.layout_products_loader)
    LinearLayout loaderLayout;

    @BindView(R.id.layout_products_error)
    LinearLayout errorLayout;

    @BindView(R.id.similar_products_layout)
    LinearLayout productsLayout;

    @BindView(R.id.txt_products_error_msg)
    TextView txtErrorMsg;

    @BindView(R.id.btn_products_try_again)
    MaterialButton btnTryAgain;

    public Uri imageUriReference;

    public SimilarProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_similar_products, container, false);
        ButterKnife.bind(this, v);
        getDataFromApi();
        return v;
    }

    public void receiveBricks(List<Brick> bricks) {
        recyclerViewProducts.setAdapter(new SimilarItemsAdapter(bricks, null,null, this));
        switchLoaderAndListLayout();
    }

    

    public void receiveRoofTiles(List<RoofTile> roofTiles) {
        recyclerViewProducts.setAdapter(new SimilarItemsAdapter(null, roofTiles,null, this));
        switchLoaderAndListLayout();
    }

    public void cantReceiveProducts(String message) {
        loaderLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
        txtErrorMsg.setText(message);
        btnTryAgain.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
            ((MainActivity) getActivity()).btmNav.setVisibility(View.VISIBLE);
        });
    }



    private void getDataFromApi() {
        MainActivity mainActivity = (MainActivity) getActivity();
        imageUriReference = mainActivity.imageUri;
        SimilarProductsStorage storage = new SimilarProductsStorage();
        storage.getProducts(this, mainActivity.cropImageUri);
    }


    private void switchLoaderAndListLayout() {
        loaderLayout.setVisibility(View.GONE);
        productsLayout.setVisibility(View.VISIBLE);
    }


}