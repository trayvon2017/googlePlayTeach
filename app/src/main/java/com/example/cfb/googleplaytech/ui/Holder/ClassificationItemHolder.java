package com.example.cfb.googleplaytech.ui.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.ClassificationInfo;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.StringUtils;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by cfb on 2018/6/25.
 */

public class ClassificationItemHolder extends BaseHolder<ClassificationInfo>{
    public ImageView[] imageViews ;
    private TextView[] textViews;
    private LinearLayout[] linearLayouts;
    /*private ImageView ivIcon1;
    private ImageView ivIcon2;
    private ImageView ivIcon3;
    private TextView tvName1;
    private TextView tvName2;
    private TextView tvName3;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;*/

    @Override
    protected View initItemView() {
        View view = UIUtils.inflate(R.layout.item_classification_page);
        return view;
    }

    @Override
    public void setData(final ClassificationInfo info) {
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
        final String[] names = new String[3];
        names[0]= info.name1;
        names[1]= info.name2;
        names[2]= info.name3;
        String[] urls = new String[3];
        urls[0]= info.url1;
        urls[1]= info.url2;
        urls[2]= info.url3;
        for (int i =0;i<3;i++){
            if (!StringUtils.isEmpty(names[i])){
                bitmapUtils.display(imageViews[i], HttpHelper.URL + "image?name=" + urls[i]);
                textViews[i].setText(names[i]);
                linearLayouts[i].setVisibility(View.VISIBLE);
                final int j =i;
                linearLayouts[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(UIUtils.getContext(), names[j], Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                linearLayouts[i].setVisibility(View.INVISIBLE);
            }
        }
       /* if (!StringUtils.isEmpty(info.name1)){
            bitmapUtils.display(ivIcon1, HttpHelper.URL + "image?name=" + info.url1);
            tvName1.setText(info.name1);
            ll_1.setVisibility(View.VISIBLE);
            ll_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UIUtils.getContext(), info.name1, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            ll_1.setVisibility(View.INVISIBLE);
        }
        if (!StringUtils.isEmpty(info.name2)){
            bitmapUtils.display(ivIcon2, HttpHelper.URL + "image?name=" + info.url2);
            tvName2.setText(info.name2);
            ll_2.setVisibility(View.VISIBLE);
            ll_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UIUtils.getContext(), info.name2, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            ll_2.setVisibility(View.INVISIBLE);
        }
        if (!StringUtils.isEmpty(info.name3)){
            bitmapUtils.display(ivIcon3, HttpHelper.URL + "image?name=" + info.url3);
            tvName3.setText(info.name3);
            ll_3.setVisibility(View.VISIBLE);
            ll_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UIUtils.getContext(), info.name3, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            ll_3.setVisibility(View.INVISIBLE);
        }
*/


    }

    @Override
    public void findViewById() {
        imageViews = new ImageView[3];
        textViews = new TextView[3];
        linearLayouts = new LinearLayout[3];
        imageViews[0] =  (ImageView) mRootView.findViewById(R.id.iv_icon_1);
        imageViews[1]= (ImageView) mRootView.findViewById(R.id.iv_icon_2);
        imageViews[2] = (ImageView) mRootView.findViewById(R.id.iv_icon_3);

        textViews[0] = (TextView) mRootView.findViewById(R.id.tv_name_1);
        textViews[1]= (TextView) mRootView.findViewById(R.id.tv_name_2);
        textViews[2] = (TextView) mRootView.findViewById(R.id.tv_name_3);

        linearLayouts[0] = (LinearLayout) mRootView.findViewById(R.id.ll_1);
        linearLayouts[1]= (LinearLayout) mRootView.findViewById(R.id.ll_2);
        linearLayouts[2] = (LinearLayout) mRootView.findViewById(R.id.ll_3);
    }
}
