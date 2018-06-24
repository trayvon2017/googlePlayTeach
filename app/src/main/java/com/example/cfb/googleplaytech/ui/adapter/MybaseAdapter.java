package com.example.cfb.googleplaytech.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.ui.Holder.BaseHolder;
import com.example.cfb.googleplaytech.ui.Holder.MoreHolder;
import com.example.cfb.googleplaytech.ui.fragment.HomePageFragment;
import com.example.cfb.googleplaytech.utils.UIUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by fbfatboy on 2018/6/21.
 */

public abstract class MybaseAdapter<T> extends BaseAdapter {
    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_MORE = 1;
    public ArrayList<T> mData;

    public MybaseAdapter(ArrayList<T> mData) {
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return ITEM_TYPE_MORE;
        }
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == ITEM_TYPE_MORE) {
            // TODO: 2018/6/21 加载more的页面
            MoreHolder moreHolder = new MoreHolder(hasMore());
            if (hasMore()) {
                loadMore(moreHolder);
            }
            return moreHolder.itemView;
        } else {
            BaseHolder holder;
            if (convertView == null) {
                holder = initHolder();
                convertView = holder.itemView;
            } else {
                holder = (BaseHolder) convertView.getTag();
            }
            holder.setData(getItem(position));
            return convertView;
        }


    }

    /**
     * @return true表示当前页面有更多数据, false反之
     */
    public boolean hasMore() {
        return true;
    }

    public abstract BaseHolder initHolder();

    private boolean isLoadingMore = false;

    /**
     * 刷新到最后一条数据的时候,进行加载更多的操作
     */
    public void loadMore(final MoreHolder moreHolder) {
        if (!isLoadingMore) {
            isLoadingMore = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //具体怎么请求交给子类去做,根据返回值判断是否已经得到数据,加载到的是list
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                if (moreData.size() < 20) {
                                    moreHolder.setData(MoreHolder.STATE_NO_MORE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    moreHolder.setData(MoreHolder.STATE_HAVE_MORE);
                                }
                                //加载到了数据,添加进去mData,更新视图
                                mData.addAll(moreData);
                                MybaseAdapter.this.notifyDataSetChanged();
                            } else {
                                //为null说明请求数据出错,进入到加载失败的状态
                                moreHolder.setData(MoreHolder.STATE_ERROR);
                            }
                            isLoadingMore = false;
                        }
                    });
                }
            }).start();
        }

    }

    /**
     * 加载更多数据,运行在子线程,不可更新ui
     *
     * @return 加载到的数据
     */
    public abstract ArrayList<T> onLoadMore();
    public int getListSize(){
        return mData.size();
    }

}
