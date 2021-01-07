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

public class BrickViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_brick_brand)
    TextView brandTxt;
    @BindView(R.id.txt_brick_desc)
    TextView descTxt;
    @BindView(R.id.txt_brick_name)
    TextView nameTxt;
    @BindView(R.id.expandable_brick_layout)
    MaterialCardView expandableLayout;
    @BindView(R.id.btn_brick_details)
    MaterialButton detailsBtn;
    /*
    @BindView(R.id.btn_brick_web)
    MaterialButton webBtn;
     */
    @BindView(R.id.img_brick)
    ImageView imageViewBrick;

    public BrickViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
