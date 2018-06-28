package com.example.cfb.googleplaytech.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.cfb.googleplaytech.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by cfb on 2018/6/27.
 */

public class MyFlowLayout extends ViewGroup {
    private ArrayList<Line> mLines;
    private Line mCurrentLine;
    private static final int HORIZONTAL_SPACE = UIUtils.dip2px(5);
    private static final int VERTICAL_SPACE = UIUtils.dip2px(5);
    private int totalWidth;

    //    private int currentMaxHeight;
    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bootom) {
        if (mLines!=null&&mLines.size()>0){
            left += getPaddingLeft();
            top += getPaddingTop();

            for (int i=0;i<mLines.size();i++){
                //先写第一行的
                layout(i,left,top);
                top = top + mLines.get(i).maxHeight+VERTICAL_SPACE;

            }

        }
    }

    /**
     * line里面的子控件布局
     * @param lineNum
     * @param left
     * @param top
     */
    private void layout(int lineNum, int left, int top) {
        //未调整是首个子控件的l,t
        Line line = mLines.get(lineNum);
        if (line.size()>0){
            for (int i=0;i<line.size();i++){
                //放置每一行的各个子控件的
                View childView = line.getChildAt(i);
                int measuredWidth = childView.getMeasuredWidth()+line.getWidthOffSet();//实际宽度
                int measuredHeight = childView.getMeasuredHeight();
                int topOffset = (line.maxHeight-measuredHeight)/2;
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                int heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
                childView.measure(widthMeasureSpec,heightMeasureSpec);//测量
                childView.layout(left,top+topOffset,left+measuredWidth,top+topOffset+measuredHeight);
                left = left +measuredWidth+HORIZONTAL_SPACE;
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //初始化当前行
        mCurrentLine = new Line();
        //初始化存储所有行的mlines
        mLines = new ArrayList<>();
        //获取当前控件的宽度---高度不确定吗??
        int size = MeasureSpec.getSize(widthMeasureSpec);
        //除去有可能被添加的左右padding
        totalWidth = size - getPaddingLeft()-getPaddingRight();
        int childCount = getChildCount();
        int usedWidth = 0;
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            //对首个单独判断
            if (mCurrentLine.size()==0){
                //第一个子view或者第一个子view加上间距之后不小于totalWidth
                if (measuredWidth+HORIZONTAL_SPACE>= totalWidth){
                    //添加到当前行然后换行
                    addToCurrentLine(child);
                    newLine();
                    usedWidth = 0;
                }else {
                    //添加到当前行
                    addToCurrentLine(child);
                    //记录当前已使用宽度
                    usedWidth += measuredWidth;
                }
            }else {
                //因为不是第一个所以一定要添加间距
                usedWidth += (measuredWidth+HORIZONTAL_SPACE);
                if (usedWidth> totalWidth){//直接超出,||直接添加到下一行??好像不行吧,如果它本身就比
                    addToNextLine(child);//添加到下一行
                    usedWidth = measuredWidth;
                }else if (usedWidth == totalWidth){ //
                    addToCurrentLine(child);
                    newLine();
                    usedWidth =0;
                }else {
                    addToCurrentLine(child);
                }
            }

        }
        //确保添加最后一行
        if (mCurrentLine.size()>0&&!mLines.contains(mCurrentLine)){
            mLines.add(mCurrentLine);
        }
        //配置当前布局的widthMeasureSpec,和heightMeasureSpec
        //其中widthMeasureSpec使用之前的,heightMeasureSpec的高度需要重新配置

        if (mLines!=null&&mLines.size()>0){
            int measuredHeight = 0;
            for (Line line :mLines){
                measuredHeight += line.maxHeight;
            }
            //不要忽略padding
            measuredHeight = getPaddingTop()+getPaddingBottom();
            heightMeasureSpec= MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 保存当前行,新加一行,添加
     * @param child
     */
    private void addToNextLine(View child) {
        newLine();
        mCurrentLine.add(child);
    }

    /**
     * 换行
     */
    private void newLine() {
        //先保存当前行,然后换行
        mLines.add(mCurrentLine);
        mCurrentLine = new Line();
    }

    /**
     *
     * @param child  要添加的子view
     */
    private void addToCurrentLine(View child) {
        mCurrentLine.add(child);
    }

    class Line{
        int maxHeight;
        ArrayList<View> currentLine ;
        Line(){
            currentLine = new ArrayList<>();
        }
        void add(View view){
            currentLine.add(view);
            int measuredHeight = view.getMeasuredHeight();
            maxHeight = (maxHeight> measuredHeight ?maxHeight: measuredHeight);
        }

        public int size() {
            return currentLine.size();
        }
        View getChildAt(int index){
            return currentLine.get(index);
        }
        int getWidthOffSet(){
            if (currentLine.size()==1){
                return totalWidth-getChildMeasuredWidth(0);
            }else {
                float v = (float) (totalWidth - getTotalWidth()) / size();
                return (int)(v+0.5f);
            }
        }
        int getChildMeasuredWidth(int index){
            return getChildAt(index).getMeasuredWidth();
        }

        /**
         * 计算所有子控件的真实宽度和并且叠加verticalSpace
         * @return
         */
        int getTotalWidth(){
            int total=0;
            for (int i=0;i<size();i++){
                total += getChildMeasuredWidth(i);
            }
            total += HORIZONTAL_SPACE*(size()-1);
            return total;
        }

    }
}
