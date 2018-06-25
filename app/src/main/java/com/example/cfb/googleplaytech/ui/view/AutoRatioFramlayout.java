package com.example.cfb.googleplaytech.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.cfb.googleplaytech.R;

/**
 * Created by fbfatboy on 2018/6/24.
 */

public class AutoRatioFramlayout extends FrameLayout {

    private float mRatio;

    public AutoRatioFramlayout(@NonNull Context context) {
        super(context);
    }

    public AutoRatioFramlayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.AutoRatioFramlayout);
        mRatio = typedArray.getFloat(R.styleable.AutoRatioFramlayout_ratio, -1);
        typedArray.recycle();// 回收typearray, 提高性能
    }

    public AutoRatioFramlayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setmRatio(float mRatio) {
        this.mRatio = mRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRatio !=  -1 && mRatio !=0){
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (widthMode == MeasureSpec.EXACTLY&& heightMode!=MeasureSpec.EXACTLY) {
                int imageWidth = widthSize - getPaddingLeft() - getPaddingRight();
                int imageHeight = (int) (imageWidth / mRatio + 0.5f);
                int heightSize = imageHeight + getPaddingBottom() + getPaddingTop();
                //设置高度
                int measureHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
                heightMeasureSpec = measureHeightMeasureSpec;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
