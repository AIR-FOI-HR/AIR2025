package hr.foi.air.visualbrickfinder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class HelpFeaturePageAdapter extends PagerAdapter {
        private int [] mHelpCardLayouts;
        private LayoutInflater mLayoutInflater;
        private Context mContext;

        public HelpFeaturePageAdapter(int [] mHelpCardLayouts, Context mContext){
            this.mHelpCardLayouts=mHelpCardLayouts;
            this.mContext=mContext;
            mLayoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    @Override
    public int getCount() {
        return mHelpCardLayouts.length;
    }

    @Override
    public boolean isViewFromObject( View view, Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view=mLayoutInflater.inflate(mHelpCardLayouts[position], container,false);
            container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view=(View)object;
        container.removeView(view);
    }
}
