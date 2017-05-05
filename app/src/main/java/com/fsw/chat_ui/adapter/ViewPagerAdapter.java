package com.fsw.chat_ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    private int size;// 页数


    public ViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
        size = viewList == null ? 0 : viewList.size();
    }

    public void setListViews(List<View> viewList) {// 自己写的一个方法用来添加数据
        this.viewList = viewList;
        size = viewList == null ? 0 : viewList.size();
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
        ((ViewPager) arg0).removeView(viewList.get(arg1 % size));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        try {
            ((ViewPager) container).addView(viewList.get(position % size), 0);
        } catch (Exception e) {
        }
        return viewList.get(position % size);
    }
}