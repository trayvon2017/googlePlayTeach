package com.example.cfb.googleplaytech.ui.Holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.domain.AppInfo;
import com.example.cfb.googleplaytech.domain.SubjectInfo;
import com.example.cfb.googleplaytech.http.HttpHelper;
import com.example.cfb.googleplaytech.utils.BitmapHelper;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public class SubjectPageHolder extends BaseHolder<SubjectInfo> {


    private ImageView mSubjectImage;
    private TextView mSubjectDes;


    @Override
    protected View initItemView() {
        return UIUtils.inflate(R.layout.item_subject_page);
    }

    @Override
    public void setData(SubjectInfo data) {
        BitmapUtils bitmapUtils = BitmapHelper.getBitmapUtils();
//        BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
        bitmapUtils.display(mSubjectImage,HttpHelper.URL + "image?name="
                + data.url);
        mSubjectDes.setText(data.des);
    }

    @Override
    public void findViewById() {
        mSubjectImage = (ImageView) itemView.findViewById(R.id.iv_subject_pic);
        mSubjectDes = (TextView) itemView.findViewById(R.id.tv_subject_des);

    }
}
