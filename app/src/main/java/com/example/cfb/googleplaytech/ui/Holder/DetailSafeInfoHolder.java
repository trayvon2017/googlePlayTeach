package com.example.cfb.googleplaytech.ui.Holder;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * Created by cfb on 2018/6/26.
 */

public class DetailSafeInfoHolder extends BaseHolder<ArrayList<AppInfo.SafeInfo>> {
    private ImageView[] safeIcons;
    private ImageView[] desIcons;
    private TextView[] deses;
    private ImageView ivIndicator;
    private LinearLayout llDetailDes;
    private boolean showDes;
    private ValueAnimator mShowAnimator;
    private ValueAnimator mHideAnimator;
    private LinearLayout.LayoutParams llDesParams;

    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.detail_app_safe_info_layout);
        return view;
    }

    @Override
    public void setData(ArrayList<AppInfo.SafeInfo> safe) {
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
        for (int i=0;i<4;i++){
            if (i<safe.size()){
                AppInfo.SafeInfo safeInfo = safe.get(i);
                //正常显示的
                bitmapUtils.display(safeIcons[i], HttpHelper.URL + "image?name="
                        + safeInfo.safeUrl);
                bitmapUtils.display(desIcons[i], HttpHelper.URL + "image?name="
                        + safeInfo.safeDesUrl);
                deses[i].setText(safeInfo.safeDes);

            }else {
                //隐藏
                safeIcons[i].setVisibility(GONE);
                deses[i].setVisibility(GONE);
                desIcons[i].setVisibility(GONE);
            }
        }
        /*initAnimator(getDesMeasuredHeight());
                llDesParams.height = 0;
        llDetailDes.setLayoutParams(llDesParams);*/

    }

    @Override
    public void findViewById() {
        //初始化safeICON
        safeIcons = new ImageView[4];
        safeIcons[0] = (ImageView) mRootView.findViewById(R.id.iv_detail_safe_icon0);
        safeIcons[1] = (ImageView) mRootView.findViewById(R.id.iv_detail_safe_icon1);
        safeIcons[2] = (ImageView) mRootView.findViewById(R.id.iv_detail_safe_icon2);
        safeIcons[3] = (ImageView) mRootView.findViewById(R.id.iv_detail_safe_icon3);
        //初始化DESicon
        desIcons = new ImageView[4];
        desIcons[0] = (ImageView) mRootView.findViewById(R.id.iv_detail_des_icon0);
        desIcons[1] = (ImageView) mRootView.findViewById(R.id.iv_detail_des_icon1);
        desIcons[2] = (ImageView) mRootView.findViewById(R.id.iv_detail_des_icon2);
        desIcons[3] = (ImageView) mRootView.findViewById(R.id.iv_detail_des_icon3);
        //初始化textview
        deses = new TextView[4];
        deses[0] = (TextView) mRootView.findViewById(R.id.tv_detail_safe_des0);
        deses[1] = (TextView) mRootView.findViewById(R.id.tv_detail_safe_des1);
        deses[2] = (TextView) mRootView.findViewById(R.id.tv_detail_safe_des2);
        deses[3] = (TextView) mRootView.findViewById(R.id.tv_detail_safe_des3);
        //实现点击的
        RelativeLayout rlDesToogle = (RelativeLayout) mRootView.findViewById(R.id.rl_safe_des_toogle);
        rlDesToogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toogle();
            }
        });
        //整个隐藏或者显示的des
        llDetailDes = (LinearLayout) mRootView.findViewById(R.id.ll_detail_safe_des);



        //指示器箭头
        ivIndicator = (ImageView) mRootView.findViewById(R.id.iv_detail_safe_indicator);
//        初始化属性动画
        initAnimator(getDesMeasuredHeight());
//                llDetailDes.setVisibility(GONE);
        llDesParams.height = 0;
        llDetailDes.setLayoutParams(llDesParams);
    }

    /**
     * 控制safedes页面的显示或者隐藏
     */
    private void toogle() {
        if (showDes){
            showDes = false;
            //隐藏safedes
//            llDetailDes.setVisibility(GONE);
            ivIndicator.setImageResource(R.drawable.arrow_down);
            mHideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();
                    llDesParams.height = value;
                    Log.d(TAG, "onAnimationUpdate: 隐藏height:"+llDesParams.height);
                    llDetailDes.setLayoutParams(llDesParams);
                }
            });
            mHideAnimator.start();

        }else {
            showDes = true;
//            llDetailDes.setVisibility(View.VISIBLE);
            ivIndicator.setImageResource(R.drawable.arrow_up);
            mShowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (int) valueAnimator.getAnimatedValue();
                    llDesParams.height = value;
                    Log.d(TAG, "onAnimationUpdate: 显示height:"+llDesParams.height);

                    llDetailDes.setLayoutParams(llDesParams);
                }
            });
            mShowAnimator.start();

        }
    }

    /**
     * 测量要显示或隐藏空间的高度
     * @return
     */
    private int getDesMeasuredHeight(){
        llDetailDes.measure(0,0);
        int measuredHeight = llDetailDes.getMeasuredHeight();
        //
        llDesParams = (LinearLayout.LayoutParams) llDetailDes.getLayoutParams();
        return measuredHeight;
    }
    /**
     * 初始化属性动画
     */
    private void initAnimator(int measuredHeight){
        mShowAnimator = ValueAnimator.ofInt(0, measuredHeight);
        mShowAnimator.setDuration(2000);
        mHideAnimator = ValueAnimator.ofInt(measuredHeight, 0);
        mHideAnimator.setDuration(2000);
    }

}
