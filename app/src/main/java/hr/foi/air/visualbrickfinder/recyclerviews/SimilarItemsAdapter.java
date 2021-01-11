package hr.foi.air.visualbrickfinder.recyclerviews;


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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import hr.foi.air.database.entities.Picture;
import hr.foi.air.visualbrickfinder.FavoritesFragment;
import hr.foi.air.visualbrickfinder.HistoryFragment;
import hr.foi.air.visualbrickfinder.HistoryProductsFragment;
import hr.foi.air.visualbrickfinder.R;
import hr.foi.air.visualbrickfinder.SimilarProductsFragment;
import hr.foi.air.visualbrickfinder.database.ProductHistoryStorage;
import hr.foi.air.webservicefrontend.products.Brick;
import hr.foi.air.webservicefrontend.products.RoofTile;

public class SimilarItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Brick> brickList;
    private List<RoofTile> roofTileList;
    private List<Picture> pictureList;
    private ViewGroup viewGroup;
    private int saveHeight;
    private HistoryFragment historyCaller = null;
    private FavoritesFragment favoritesCaller = null;
    private HistoryProductsFragment historyProductsFragmentCaller = null;
    private SimilarProductsFragment similarProductsFragmentCaller = null;

    public SimilarItemsAdapter(List<Brick> brickList, List<RoofTile> roofTileList, List<Picture> pictureList, SimilarProductsFragment similarProductsFragmentCaller) {
        this.brickList = brickList;
        this.roofTileList = roofTileList;
        this.pictureList= pictureList;
        this.similarProductsFragmentCaller = similarProductsFragmentCaller;
    }

    public SimilarItemsAdapter(List<Brick> brickList, List<RoofTile> roofTileList, List<Picture> pictureList, HistoryFragment historyCaller) {
        this.brickList = brickList;
        this.roofTileList = roofTileList;
        this.pictureList= pictureList;
        this.historyCaller = historyCaller;
    }

    public SimilarItemsAdapter(List<Brick> brickList, List<RoofTile> roofTileList, List<Picture> pictureList, FavoritesFragment historyCaller) {
        this.brickList = brickList;
        this.roofTileList = roofTileList;
        this.pictureList= pictureList;
        this.favoritesCaller = historyCaller;
    }

    public SimilarItemsAdapter(List<Brick> brickList, List<RoofTile> roofTileList, List<Picture> pictureList, HistoryProductsFragment historyProductsFragmentCaller) {
        this.brickList = brickList;
        this.roofTileList = roofTileList;
        this.pictureList= pictureList;
        this.historyProductsFragmentCaller = historyProductsFragmentCaller;
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
                return new PictureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false));
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
                if(currentBrick.getFlagFavorite() == 0) {
                    brickViewHolder.favoriteBtn.setIconTintResource(R.color.greyButtonColor);
                } else {
                    brickViewHolder.favoriteBtn.setIconTintResource(R.color.cherryWienerberger);
                }
                File file = new File(currentBrick.getLocalImageUrl());
                Picasso.get().load(file).resize(400, 400).centerCrop().into(brickViewHolder.imageViewBrick, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(currentBrick.getWebsiteImageUrl()).resize(400, 400).centerCrop().into(brickViewHolder.imageViewBrick);
                    }
                });
                setExpandCollapseAnimation(brickViewHolder.detailsBtn, brickViewHolder.expandableLayout);
                String itemName = brickViewHolder.nameTxt.getText().toString();
                setFavoritesButton(brickViewHolder.favoriteBtn, itemName);


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
                if(currentRoofTile.getFlagFavorite() == 0) {
                    roofTileViewHolder.favoriteBtn.setIconTintResource(R.color.greyButtonColor);
                } else {
                    roofTileViewHolder.favoriteBtn.setIconTintResource(R.color.cherryWienerberger);
                }

                Picasso.get().load(currentRoofTile.getWebsiteImageUrl()).into(roofTileViewHolder.imageViewRoofTile);
                setExpandCollapseAnimation(roofTileViewHolder.detailsBtn, roofTileViewHolder.expandableLayout);
                String rooftileName = roofTileViewHolder.nameTxt.getText().toString();
                setFavoritesButton(roofTileViewHolder.favoriteBtn, rooftileName);
                /*
                roofTileViewHolder.webBtn.setOnClickListener(v -> {
                    //TODO: set valid links
                    v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wienerberger.co.uk/")));
                });
                 */
                break;

            case 2:
                PictureViewHolder pictureViewHolder = (PictureViewHolder) holder;
                Picture currentPicture = pictureList.get(position);
                pictureViewHolder.dateTxt.setText(String.valueOf(currentPicture.getPictureDate()));
                Picasso.get().load(currentPicture.getImageUri()).into(pictureViewHolder.imageView);
                pictureViewHolder.id=currentPicture.getId();
                pictureViewHolder.historyFragment= historyCaller;
                break;
        }
    }

    private void setFavoritesButton(MaterialButton button, String itemName) {

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ProductHistoryStorage productHistoryStorage = new ProductHistoryStorage();
                if(button.getIconTint().getDefaultColor() == -5046272 ){
                    button.setIconTintResource(R.color.greyButtonColor);
                    if(favoritesCaller != null) {
                        productHistoryStorage.setProductAsNOFavorite(favoritesCaller, itemName);
                    } else {
                        if (historyProductsFragmentCaller != null){
                            productHistoryStorage.setProductAsNOFavorite(historyProductsFragmentCaller, itemName);
                        } else {
                            if(similarProductsFragmentCaller != null)
                                productHistoryStorage.setProductAsNOFavorite(similarProductsFragmentCaller, itemName);
                        }
                    }
                } else
                    if(button.getIconTint().getDefaultColor() == -2697257){
                        button.setIconTintResource(R.color.cherryWienerberger);
                        if(favoritesCaller != null) {
                            productHistoryStorage.setProductAsFavorite(favoritesCaller, itemName);
                        } else {
                            if(historyProductsFragmentCaller != null){
                                productHistoryStorage.setProductAsFavorite(historyProductsFragmentCaller, itemName);
                            } else {
                                if(similarProductsFragmentCaller != null){
                                    productHistoryStorage.setProductAsFavorite(similarProductsFragmentCaller, itemName);
                                }
                            }
                        }
                    }
            }
        });
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
