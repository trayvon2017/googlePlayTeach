package com.example.cfb.googleplaytech.ui.Holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View.MeasureSpec;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

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
    public void setData(final AppInfo appInfo) {
        mdata = appInfo;
        tvAuthor.setText(appInfo.author);
        tvDes.setText(appInfo.des);
        tvDesLayoutParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();

//        ArrayList<Object> objects = new ArrayList<>();
//        Log.d(TAG, "setData: 空集合size"+objects.size());

        //这种方法也可以 并且首次加载不会更新出完整textview,但是有风险,
        //如果我们设置的最低高度甚至比完整展示的高度还要低的时候
        //因为此处无法获取到正常显示的高度,所以会造成textview的文字未完全占用空间
        /*tvDesLayoutParams.height = getMiniHeight(appInfo.des);
        tvDes.setLayoutParams(tvDesLayoutParams);*/
        //这种方法首次加载会先更新出完整的textView然后才变为我们想要的缩略样式
        /*tvDes.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: post方法中的measureWidth" + tvDes.getMeasuredWidth());
                int miniHeight = getMiniHeight(appInfo.des);
                int maxHeight = getMaxHeight(appInfo.des);
                //仅当正常显示高度大于要设置的最固定行数高度的时候才设置成最小高度,否则不做处理
                if (maxHeight > miniHeight) {
                    tvDesLayoutParams.height = miniHeight;
                    tvDes.setLayoutParams(tvDesLayoutParams);
                }
            }
        });*/
        /*ViewTreeObserver observer = tvDes.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //底层可能回调多次,避免重复监听,此处不能使用外面的observer直接remove需要重新获取一个observer
                tvDes.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.d(TAG, "run: post方法中的measureWidth" + tvDes.getMeasuredWidth());
                int miniHeight = getMiniHeight(appInfo.des);
                int maxHeight = getMaxHeight(appInfo.des);
                //仅当正常显示高度大于要设置的最固定行数高度的时候才设置成最小高度,否则不做处理
                if (maxHeight > miniHeight) {
                    tvDesLayoutParams.height = miniHeight;
                    tvDes.setLayoutParams(tvDesLayoutParams);
                }
                Log.d(TAG, "onGlobalLayout: global回调的width:" + tvDes.getMeasuredWidth());
            }
        });*/

        showTotalDes = false;

        /*int measuredWidth = tvDes.getMeasuredWidth();
        Log.d(TAG, "setData里面的 measuredWidth+" + measuredWidth);*/

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
        maxHeight = getMaxHeight(getData().des);
        miniHeight = getMiniHeight(getData().des);
        if (maxHeight < miniHeight) {
            return;
        }
        ValueAnimator animator;
        if (showTotalDes) {
            //隐藏
            showTotalDes = false;
            animator = ValueAnimator.ofInt(maxHeight, miniHeight);

        } else {
            showTotalDes = true;
            animator = ValueAnimator.ofInt(miniHeight, maxHeight);

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
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //更换箭头;scrollView滚动到底部
                ivIndicator.setImageResource(showTotalDes?R.drawable.arrow_up:R.drawable.arrow_down);
                processScrollView();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * 关闭或者拉起
     */
    private void processScrollView() {
        View parent = (View) tvDes.getParent();
        while (!(parent instanceof ScrollView)){
            parent = (View) parent.getParent();
        }
        ScrollView scrollView = (ScrollView) parent;
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    /**
     * 计算text显示3行数据时候的高度
     *
     * @param data
     * @return
     */
    private int getMiniHeight(String data) {
        int width = tvDes.getMeasuredWidth();
        Log.d(TAG, "目前toogle中执行的width: +" + width);
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(data);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setMaxLines(3);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 计算text显示完整数据时候的高度
     *
     * @param data
     * @return
     */
    private int getMaxHeight(String data) {
        int width = tvDes.getMeasuredWidth();
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(data);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        textView.setMaxLines(3);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    private AppInfo mdata;

    private AppInfo getData() {
        return mdata;
    }
}
