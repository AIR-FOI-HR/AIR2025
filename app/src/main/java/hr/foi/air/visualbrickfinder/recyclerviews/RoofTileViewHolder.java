package hr.foi.air.visualbrickfinder.recyclerviews;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.visualbrickfinder.R;

public class RoofTileViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_roof_tile_brand)
    TextView brandTxt;
    @BindView(R.id.txt_roof_tile_desc)
    TextView descTxt;
    @BindView(R.id.txt_roof_tile_dimensions)
    TextView dimensionsTxt;
    @BindView(R.id.txt_roof_tile_name)
    TextView nameTxt;
    @BindView(R.id.expandable_roof_tile_layout)
    MaterialCardView expandableLayout;
    @BindView(R.id.btn_roof_tile_details)
    MaterialButton detailsBtn;
    /*
    @BindView(R.id.btn_roof_tile_web)
    MaterialButton webBtn;
     */
    @BindView(R.id.img_roof_tile)
    ImageView imageViewRoofTile;

    public RoofTileViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
