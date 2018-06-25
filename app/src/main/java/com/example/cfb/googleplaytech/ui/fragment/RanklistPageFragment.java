package com.example.cfb.googleplaytech.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cfb.googleplaytech.http.protocol.RankPageProtocol;
import com.example.cfb.googleplaytech.ui.view.FlowLayout;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.utils.DrawableUtils;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cfb on 2018/6/20.
 */

public class RanklistPageFragment extends BaseFragment {


    private ArrayList<String> data;

    @Override
    public LoadingPage.LoadResult onLoad() {
        RankPageProtocol rankPageProtocol = new RankPageProtocol();
        data = rankPageProtocol.getData(0);
        return checkData(data);
    }

    @Override
    public View onCreateSuccessView() {
        // 支持上下滑动
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flow = new FlowLayout(UIUtils.getContext());

        int padding = UIUtils.dip2px(10);
        flow.setPadding(padding, padding, padding, padding);// 设置内边距

        flow.setHorizontalSpacing(UIUtils.dip2px(6));// 水平间距
        flow.setVerticalSpacing(UIUtils.dip2px(8));// 竖直间距

        for (int i = 0; i < data.size(); i++) {
            final String keyword = data.get(i);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyword);

            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);// 18sp
            view.setPadding(padding, padding, padding, padding);
            view.setGravity(Gravity.CENTER);

            // 生成随机颜色
            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            int color = 0xffcecece;// 按下后偏白的背景色

            // GradientDrawable bgNormal = DrawableUtils.getGradientDrawable(
            // Color.rgb(r, g, b), UIUtils.dip2px(6));
            // GradientDrawable bgPress = DrawableUtils.getGradientDrawable(
            // color, UIUtils.dip2px(6));
            // StateListDrawable selector = DrawableUtils.getSelector(bgNormal,
            // bgPress);

            StateListDrawable selector = DrawableUtils.getSelector(
                    Color.rgb(r, g, b), color, UIUtils.dip2px(6));
            view.setBackgroundDrawable(selector);

            flow.addView(view);

            // 只有设置点击事件, 状态选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), keyword,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        scrollView.addView(flow);
        return scrollView;
    }

}
