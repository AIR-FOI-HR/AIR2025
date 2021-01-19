package hr.foi.air.visualbrickfinder;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpFragment extends Fragment {

    @BindView(R.id.help_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.help_dots)
    LinearLayout mLinearOfDots;
    private int[] helpCardLayouts = {R.layout.help_take_a_photo, R.layout.help_select_from_gallery, R.layout.help_good_examples, R.layout.help_bad_examples};
    private HelpFeaturePageAdapter mHelpFeaturePageAdapter;
    private ImageView[] mDots;
    public HelpFragment() {
        // Required empty public constructor
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, v);
        mViewPager.setPadding(0, 80, 0, 80);
        mHelpFeaturePageAdapter = new HelpFeaturePageAdapter(helpCardLayouts, getActivity());

        mViewPager.setAdapter(mHelpFeaturePageAdapter);
        createDots(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return v;
    }
    private void createDots(int currentPosition) {
        if (mLinearOfDots != null) mLinearOfDots.removeAllViews();
        mDots = new ImageView[helpCardLayouts.length];

        for (int i = 0; i < helpCardLayouts.length; i++) {
            mDots[i] = new ImageView(getActivity());
            if (i == currentPosition) {
                mDots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
            } else {
                mDots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.inactive_dot));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 60, 10, 108);
            mLinearOfDots.addView(mDots[i], params);
        }
    }

}