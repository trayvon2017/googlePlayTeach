package com.example.cfb.googleplaytech.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.cfb.googleplaytech.R;

/**
 * Created by cfb on 2018/6/20.
 */

public class BaseActivity extends AppCompatActivity {
    public void showActionBarLogo() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);//设置logo的图标
        actionBar.setDisplayUseLogoEnabled(true);//设置logo可以显示
        actionBar.setDisplayShowHomeEnabled(true);
    }
}

