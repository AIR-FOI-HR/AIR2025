package hr.foi.air.visualbrickfinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.database.MyDatabase;
import hr.foi.air.database.data.MockData;

//import static hr.foi.air.database.MyDatabase.getInstance;

public class FavoritesFragment extends Fragment {

    @BindView(R.id.test_button)
    Button testButton;

    public static MyDatabase database;

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

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mockData();
            }
        });
        return v;
    }

    private void mockData() {
        MockData.writeAll(this.getContext());
    }
}