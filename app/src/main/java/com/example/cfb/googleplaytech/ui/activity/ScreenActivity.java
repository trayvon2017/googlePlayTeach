package com.example.cfb.googleplaytech.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

public class ScreenActivity extends BaseActivity {

    private int index;
    private ViewPager mVpScreens;
    private BitmapUtils mBitmapUtils;
    private ArrayList<String> screens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActionBarLogo();
        setContentView(R.layout.activity_screen);
        Intent intent = getIntent();
        index = intent.getIntExtra("index",-1);
        screens = intent.getStringArrayListExtra("screens");
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        initView();
    }

    private void initView() {
        mVpScreens = (ViewPager) findViewById(R.id.vp_screens);
        mVpScreens.setAdapter(new MyPagerAdapter());
        mVpScreens.setCurrentItem(index);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(UIUtils.getContext());
            mBitmapUtils.display(imageView, HttpHelper.URL + "image?name="
                    + screens.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return screens.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
