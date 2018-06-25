package com.example.cfb.googleplaytech.ui.Holder;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.ui.view.AutoRatioFramlayout;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/25.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {
    private ArrayList<String> mPictures ;
    private BitmapUtils bitmapUtils;
    private ViewPager vpHeader;
    private LinearLayout llIndicator;
    private Handler handler;

    @Override
    protected View initItemView() {
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams rlParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                UIUtils.dip2px(150));
        rlRoot.setLayoutParams(rlParams);
        //添加viewpager
        vpHeader = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        rlRoot.addView(vpHeader,vpParams);
//        vpHeader.setAdapter(new MyPageAdapter());
        //初始化indicator
        llIndicator = new LinearLayout(UIUtils.getContext());
        llIndicator.setOrientation(LinearLayout.HORIZONTAL);
        int padding = UIUtils.dip2px(10);
        llIndicator.setPadding(padding,padding,padding,padding);
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        rlRoot.addView(llIndicator,llParams);
        AutoRatioFramlayout autoRatioFramlayout = new AutoRatioFramlayout(UIUtils.getContext());
        autoRatioFramlayout.setmRatio(2.65f);

        return rlRoot;
    }
    private int preSelected ;
    @Override
    public void setData(ArrayList<String> pictures) {
        mPictures = pictures;
        vpHeader.setAdapter(new MyPageAdapter());
        vpHeader.setCurrentItem(10000*mPictures.size());
        preSelected = vpHeader.getCurrentItem();
        for (int i=0;i<mPictures.size();i++){
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setImageResource(i==0?R.drawable.indicator_selected:R.drawable.indicator_normal);
            llIndicator.addView(imageView);
        }
        vpHeader.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageView currentPoint = (ImageView) llIndicator.getChildAt(position % mPictures.size());
                currentPoint.setImageResource(R.drawable.indicator_selected);
                ImageView prePoint = (ImageView) llIndicator.getChildAt(preSelected % mPictures.size());
                prePoint.setImageResource(R.drawable.indicator_normal);
                preSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler = UIUtils.getHandler();

        handler.postDelayed(new Autotask(),3*1000);
    }
    class Autotask implements Runnable{

        @Override
        public void run() {
            int currentItem = vpHeader.getCurrentItem();
            vpHeader.setCurrentItem(++currentItem);
            handler.postDelayed(new Autotask(),3*1000);

        }
    }


    @Override
    public void findViewById() {

    }

    private class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (bitmapUtils == null){
                bitmapUtils = BitmapHelper.getBitmapUtils();
            }
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            bitmapUtils.display(imageView, HttpHelper.URL + "image?name="
                    + mPictures.get(position%mPictures.size()));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
