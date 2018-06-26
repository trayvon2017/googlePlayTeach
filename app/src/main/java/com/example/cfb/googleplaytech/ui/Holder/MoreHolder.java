package com.example.cfb.googleplaytech.ui.Holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.utils.UIUtils;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public class MoreHolder extends BaseHolder<Integer> {
    public static final int STATE_HAVE_MORE = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_NO_MORE = 2;
    private LinearLayout llLoadingMore;
    private TextView tvLoadingMoreError;

    public MoreHolder(boolean hasMore) {
        mRootView =initItemView();
        findViewById();
        setData(hasMore ? STATE_HAVE_MORE : STATE_NO_MORE);
    }

    @Override
    protected View initItemView() {
        return UIUtils.inflate(R.layout.item_more_layout);
    }

    @Override
    public void setData(Integer integer) {
        switch (integer) {
            case STATE_HAVE_MORE:
                //有更多 显示加载的页面
                llLoadingMore.setVisibility(View.VISIBLE);
                tvLoadingMoreError.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                //加载失败,显示加载失败页面
                llLoadingMore.setVisibility(View.GONE);
                tvLoadingMoreError.setVisibility(View.VISIBLE);
                break;
            case STATE_NO_MORE:
                //没有更多,全部隐藏
                llLoadingMore.setVisibility(View.GONE);
                tvLoadingMoreError.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void findViewById() {
        llLoadingMore = (LinearLayout) mRootView.findViewById(R.id.ll_loadingMore);
        tvLoadingMoreError = (TextView) mRootView.findViewById(R.id.tv_loadingmore_error);
    }
}
