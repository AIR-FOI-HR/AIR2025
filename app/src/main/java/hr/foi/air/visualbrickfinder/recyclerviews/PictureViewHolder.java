package hr.foi.air.visualbrickfinder.recyclerviews;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.visualbrickfinder.HistoryFragment;
import hr.foi.air.visualbrickfinder.MainActivity;
import hr.foi.air.visualbrickfinder.R;

public class PictureViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_search_date)
    TextView dateTxt;
    @BindView(R.id.img_photo)
    ImageView imageView;
    int id;
    HistoryFragment historyFragment;


    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this::onClick);
    }


    public void onClick(View view){
       historyFragment.getProductsWithId(id);
    }
}
