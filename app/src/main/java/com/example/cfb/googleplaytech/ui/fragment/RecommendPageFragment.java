package com.example.cfb.googleplaytech.ui.fragment;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cfb.googleplaytech.R;
import com.example.cfb.googleplaytech.http.protocol.RecommendPageProtocol;
import com.example.cfb.googleplaytech.ui.view.LoadingPage;
import com.example.cfb.googleplaytech.ui.view.fly.ShakeListener;
import com.example.cfb.googleplaytech.ui.view.fly.StellarMap;
import com.example.cfb.googleplaytech.utils.UIUtils;
import com.lidroid.xutils.db.sqlite.DbModelSelector;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by cfb on 2018/6/20.
 */

public class RecommendPageFragment extends BaseFragment {
    private final static int COUNT_PER_PAGE =16;
    private final static int MINIMU_PER_PAGE = 5;


    private ArrayList<String> mData;

    @Override
    public LoadingPage.LoadResult onLoad() {
        RecommendPageProtocol recommendPageProtocol = new RecommendPageProtocol();
        mData = recommendPageProtocol.getData(0);
        Log.d(TAG, "onLoad: "+mData.size());
        return checkData(mData);
    }

    @Override
    public View onCreateSuccessView() {
        //初始化
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        //设置adapter
        stellarMap.setAdapter(new MyStellarMapAdapter());
        // 随机方式, 将控件划分为9行6列的的格子, 然后在格子中随机展示
        stellarMap.setRegularity(6,9);
        //设置整个的padding
        int pix = UIUtils.dip2px(10.0F);
        stellarMap.setPadding(pix,pix,pix,pix);
        //默认显示第一页
        stellarMap.setGroup(0,true);
        //实现摇晃处理
        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellarMap.zoomIn();
            }
        });

        return stellarMap;
    }
    class MyAdapter implements StellarMap.Adapter{
        /**
         * @return 一共分多少组,一般需要根据最后一组是否可以整除分类判断
         */
        @Override
        public int getGroupCount() {
            return 0;
        }

        /**
         *
         * @param group 组index
         * @return 对应group的改组成员数量,最后一组需要根据是否可以整除具体判断
         */
        @Override
        public int getCount(int group) {
            return 0;
        }

        /**
         * 一般需要根据group和position计算出条目在mDada中对应的index
         * @param group 组index
         * @param position 成员在该组的index
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            return null;
        }

        /**
         * 自定义上滑或者下滑之后的要展现的下一页的组index
         * @param group 当前组的index
         * @param isZoomIn 动画状态,true代表上滑,动画由外向里,false下滑由里向外
         * @return 下一组的index
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
    class MyStellarMapAdapter implements StellarMap.Adapter {
        private int remainder = mData.size()%COUNT_PER_PAGE;

        /**
         * 最后一页的数量小于5并且不等于0的时候 把最后一页的数据放到前一页 总页数需要减1
         * @return
         */
        @Override
        public int getGroupCount() {
            int pageCount = mData.size()/COUNT_PER_PAGE;
            Log.d(TAG, "getGroupCount: pageCount"+pageCount);
            return remainder<MINIMU_PER_PAGE&&remainder!=0?
                    pageCount:pageCount+1;
        }

        @Override
        public int getCount(int group) {
            //除最后一页外,其他页面全部都是按照自定义的分页数
            if (group<getGroupCount()-1){
                return COUNT_PER_PAGE;
            }else {
                //最后一页需要单独处理,余数比最后一页的数量少的时候放到前一页去展示
                /*if (remainder==0){
                    return COUNT_PER_PAGE;
                }else */if (remainder <MINIMU_PER_PAGE){
                    return COUNT_PER_PAGE+remainder;
                }else {
                    return remainder;
                }
            }
        }

        @Override
        public View getView(int group, int position, View convertView) {
            Log.d(TAG, "getView: groupCount:"+getGroupCount()+"");
            final int newPosition = COUNT_PER_PAGE*group+position;
            Holder holder;
            if (convertView == null){
                convertView = UIUtils.inflate(R.layout.item_recommend_page);
                holder = new Holder();
                holder.textView = convertView.findViewById(R.id.tv_recommend);
                convertView.setTag(holder);
            }else {
                holder = (Holder)convertView.getTag();
            }

            String result = mData.get(newPosition);
            holder.textView.setText(result);
            Random random = new Random();
            // 字体随机大小, 16-25
            int size = 16 + random.nextInt(10);
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

            // 随机颜色
            // r g b, 0-255 -> 30-230, 颜色值不能太小或太大, 从而避免整体颜色过亮或者过暗
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            holder.textView.setTextColor(Color.rgb(r, g, b));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UIUtils.getContext(), mData.get(newPosition),
                            Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn){
                /*if (group>0){
                    group--;
                }else{
                    group = getGroupCount()-1;
                }*/
                group = group>0?--group:getGroupCount()-1;
            }else {
                /*if (group == getGroupCount()-1){
                    group = 0;
                }else {
                    group++;
                }*/
                group = (group == getGroupCount()-1)?0:++group;
            }
            return group;
        }
    }

    private class Holder {
        private TextView textView;
    }
}
