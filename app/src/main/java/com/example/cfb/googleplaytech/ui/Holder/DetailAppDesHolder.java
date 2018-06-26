package com.example.cfb.googleplaytech.ui.Holder;

import android.animation.ValueAnimator;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.utils.UIUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by fbfatboy on 2018/6/26.
 */

public class DetailAppDesHolder extends BaseHolder<AppInfo> {

    private RelativeLayout rlToogleDesView;
    private ImageView ivIndicator;
    private TextView tvDes;
    private TextView tvAuthor;
    private int maxHeight;
    private int miniHeight;
    private LinearLayout.LayoutParams tvDesLayoutParams;

    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.detail_app_des_layout);
        return view;
    }

    @Override
    public void setData(AppInfo appInfo) {
        tvAuthor.setText(appInfo.author);
        tvDes.setText(appInfo.des);

        tvDes.measure(0,0);
        maxHeight = tvDes.getMeasuredHeight();
        tvDesLayoutParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
        tvDes.setMaxLines(3);
        tvDes.measure(0,0);
        miniHeight= tvDes.getMeasuredHeight();

        showTotalDes = false;
//        miniHeight = getTvDesMinimumHeight(appInfo.des);
        Log.d(TAG, "setData:maxHeight: "+maxHeight+"--setData:maxHeight: "+miniHeight);


    }

    @Override
    public void findViewById() {
        rlToogleDesView = (RelativeLayout) mRootView.findViewById(R.id.rl_des_toogle);
        rlToogleDesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogle();
            }


        });
        ivIndicator = (ImageView) mRootView.findViewById(R.id.iv_des_indicator);
        tvDes = (TextView) mRootView.findViewById(R.id.tv_detail_des_des);
        tvAuthor = (TextView) mRootView.findViewById(R.id.tv_detail_des_author);

    }
    private boolean showTotalDes;
    private void toogle() {
        ValueAnimator animator;
        if (showTotalDes){
            //隐藏
            showTotalDes = false;
            animator = ValueAnimator.ofInt(maxHeight,miniHeight);

        }else {
            showTotalDes = true;
            animator = ValueAnimator.ofInt(miniHeight,maxHeight);

        }
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                tvDesLayoutParams.height = animatedValue;
                tvDes.setLayoutParams(tvDesLayoutParams);
            }
        });
        animator.start();
    }

    private int getTvDesMinimumHeight(String string){
        int width = tvDes.getMeasuredWidth();
        tvDesLayoutParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
        TextView textView = new TextView(UIUtils.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        textView.setText(string);
        textView.setMaxLines(3);
        textView.measure(0,0);
        return textView.getMeasuredHeight();
    }
}
