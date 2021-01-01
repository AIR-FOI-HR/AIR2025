package hr.foi.air.visualbrickfinder.recyclerviews;


import android.content.Intent;
import android.net.Uri;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import hr.foi.air.visualbrickfinder.R;
import hr.foi.air.visualbrickfinder.SimilarProductsFragment;
import hr.foi.air.visualbrickfinder.database.Picture;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class SimilarItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Brick> brickList;
    private List<RoofTile> roofTileList;
    private List<Picture> pictureList;
    private ViewGroup viewGroup;
    private int saveHeight;

    public SimilarItemsAdapter(List<Brick> brickList, List<RoofTile> roofTileList, List<Picture> pictureList) {
        this.brickList = brickList;
        this.roofTileList = roofTileList;
        this.pictureList= pictureList;
    }

    @Override
    public int getItemViewType(int position) {
       // return brickList == null ? 1 : 0;
        int type;
        if(brickList != null) type = 0;
        else if (roofTileList != null) type = 1;
        else type = 2;
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        switch (viewType) {
            case 0:
                return new BrickViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brick, parent, false));
            case 1:
                return new RoofTileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roof_tile, parent, false)); //TODO: change layout
            case 2:
                return new PhotographViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photograph, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                BrickViewHolder brickViewHolder = (BrickViewHolder) holder;
                Brick currentBrick = brickList.get(position);
                brickViewHolder.nameTxt.setText(currentBrick.getName());
                brickViewHolder.brandTxt.setText(currentBrick.getBrand());
                brickViewHolder.descTxt.setText(currentBrick.getDescription());
                Picasso.get().load(currentBrick.getImage()).resize(400, 400).centerCrop().into(brickViewHolder.imageViewBrick);
                setExpandCollapseAnimation(brickViewHolder.detailsBtn, brickViewHolder.expandableLayout);
                /*
                brickViewHolder.webBtn.setOnClickListener(v -> {
                    //TODO: set valid links
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wienerberger.co.uk/")));
                });
                 */
                break;

            case 1:
                RoofTileViewHolder roofTileViewHolder = (RoofTileViewHolder) holder;
                RoofTile currentRoofTile = roofTileList.get(position);
                roofTileViewHolder.nameTxt.setText(currentRoofTile.getName());
                roofTileViewHolder.brandTxt.setText(currentRoofTile.getBrand());
                roofTileViewHolder.descTxt.setText(currentRoofTile.getDescription());
                roofTileViewHolder.dimensionsTxt.setText(currentRoofTile.getDimensions());
                Picasso.get().load(currentRoofTile.getImage()).into(roofTileViewHolder.imageViewRoofTile);
                setExpandCollapseAnimation(roofTileViewHolder.detailsBtn, roofTileViewHolder.expandableLayout);
                /*
                roofTileViewHolder.webBtn.setOnClickListener(v -> {
                    //TODO: set valid links
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wienerberger.co.uk/")));
                });
                 */
                break;

            case 2:
                PhotographViewHolder photographViewHolder = (PhotographViewHolder) holder;
                Picture currentPicture = pictureList.get(position);
                photographViewHolder.dateTxt.setText(String.valueOf(currentPicture.getDate()));
                Picasso.get().load(currentPicture.getImage()).into(photographViewHolder.imageView);
                photographViewHolder.id.setText(String.valueOf(currentPicture.getId()));
                break;
        }
    }


    @Override
    public int getItemCount() {
        //return brickList == null ? roofTileList.size() : brickList.size();
        int count;
        if(brickList != null) count=brickList.size();
        else if (roofTileList != null) count=roofTileList.size();
        else count=pictureList.size();
        return count;
    }


    private void setExpandCollapseAnimation(MaterialButton btn, MaterialCardView expandableLayout) {
        btn.setOnClickListener(v -> {

            if (expandableLayout.getVisibility() == View.GONE) {
                expandableLayout.setVisibility(View.VISIBLE);
                btn.setText("Collapse");
            } else {
                ViewGroup.LayoutParams params = expandableLayout.getLayoutParams();
                saveHeight = params.height;
                params.height = 0;
                expandableLayout.setLayoutParams(params);
                btn.setText("Details");
            }
            btn.setClickable(false);

            final ChangeBounds transition = new ChangeBounds();
            transition.setDuration(600L);
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    btn.setClickable(true);
                    ViewGroup.LayoutParams params = expandableLayout.getLayoutParams();
                    if (params.height == 0) {
                        expandableLayout.setVisibility(View.GONE);
                        params.height = saveHeight;
                        expandableLayout.setLayoutParams(params);
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
            TransitionManager.beginDelayedTransition(viewGroup, transition);
        });
    }

}
