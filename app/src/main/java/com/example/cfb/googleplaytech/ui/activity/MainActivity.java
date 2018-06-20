package com.example.cfb.googleplaytech.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.ui.fragment.FragmentFactory;
import com.example.cfb.googleplaytech.ui.view.PagerTab;
import com.example.cfb.googleplaytech.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private PagerTab mPagerTab;
    private ViewPager mViewPager;
    private String[] mTabNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showActionBarLogo();

        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mPagerTab.setViewPager(mViewPager);

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.creatFragment(position);
        }

        @Override
        public int getCount() {
            return mTabNames.length;
        }
    }

    private void showActionBarLogo() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);//设置logo的图标
        actionBar.setDisplayUseLogoEnabled(true);//设置logo可以显示
        actionBar.setDisplayShowHomeEnabled(true);
    }
}
