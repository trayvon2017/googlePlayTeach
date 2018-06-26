package com.example.cfb.googleplaytech.ui.Holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.ui.activity.AppDetailActivity;
import com.example.cfb.googleplaytech.ui.activity.ScreenActivity;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by fbfatboy on 2018/6/26.
 */

public class DetailScreenHolder extends BaseHolder<ArrayList<String>> {
    private ImageView[] mScreens;
    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.detail_app_screen_layout);

        return view;
    }

    @Override
    public void setData(ArrayList<String> strings) {

    }

    public void setData(final Context context, final ArrayList<String> strings) {
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
        int length = mScreens.length;
        for (int i=0;i<length;i++){
            if (i<strings.size()){
                //有图,显示
                bitmapUtils.display(mScreens[i], HttpHelper.URL + "image?name="
                        + strings.get(i));
                final int j = i;
                mScreens[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ScreenActivity.class);
                        intent.putExtra("index",j);
                        intent.putStringArrayListExtra("screens",strings);
                        context.startActivity(intent);
                    }
                });
            }else{
                //无图,隐藏
                mScreens[i].setVisibility(GONE);
            }
        }

    }

    @Override
    public void findViewById() {
        mScreens = new ImageView[6];
        mScreens[0]= (ImageView) mRootView.findViewById(R.id.iv_detail_screen0);
        mScreens[1]= (ImageView) mRootView.findViewById(R.id.iv_detail_screen1);
        mScreens[2]= (ImageView) mRootView.findViewById(R.id.iv_detail_screen2);
        mScreens[3]= (ImageView) mRootView.findViewById(R.id.iv_detail_screen3);
        mScreens[4]= (ImageView) mRootView.findViewById(R.id.iv_detail_screen4);
        mScreens[5]= (ImageView) mRootView.findViewById(R.id.iv_detail_screen5);

    }
}
