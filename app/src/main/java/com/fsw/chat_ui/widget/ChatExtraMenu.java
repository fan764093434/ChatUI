package com.fsw.chat_ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.ExtraMenuAdapter;
import com.fsw.chat_ui.adapter.ViewPagerAdapter;
import com.fsw.chat_ui.entity.ExtraMenu;
import com.fsw.chat_ui.utils.DisplayUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatExtraMenu extends LinearLayout {

    private MagicIndicator magicIndicator;
    private ViewPager horizontalScrollMenu;
    private ViewPagerAdapter viewPagerAdapter;
    private List<ExtraMenu> dataList;
    private List<View> viewList;

    private int pageCount = 10;
    private int itemHeight;
    private int squareWidth;


    private LayoutInflater inflater;

    public ChatExtraMenu(Context context) {
        this(context, null);
    }

    public ChatExtraMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatExtraMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatExtraMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.chat_view_extra_menu, this);
        magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        horizontalScrollMenu = (ViewPager) findViewById(R.id.horizontal_scroll_menu);
        DisplayMetrics displayMetrics = DisplayUtil.getDisplayMetrics(context);
        itemHeight = DisplayUtil.dp2px(180 - 15 - 12 - 12) / 2 - DisplayUtil.dp2px(8);
        squareWidth = itemHeight - DisplayUtil.dp2px(20);
        pageCount = ((displayMetrics.widthPixels - DisplayUtil.dp2px(8) * 2 + DisplayUtil.dp2px(8)) / (squareWidth + DisplayUtil.dp2px(8))) * 2;
        dataList = new ArrayList<>();
        viewList = new ArrayList<>();
        layoutExtraMenu();
    }

    private void layoutExtraMenu() {
        int pageNum = dataList.size() % pageCount == 0 ? dataList.size() / pageCount : dataList.size() / pageCount + 1;
        for (int i = 0; i < pageNum; i++) {
            View priPhotoView = inflater.inflate(R.layout.chat_extra_menu_item, null);
            GridView grid_view = (GridView) priPhotoView.findViewById(R.id.grid_view);
            List<ExtraMenu> list = new ArrayList<>();
            for (int j = i * pageCount; j < (i + 1) * pageCount; j++) {
                if (j < dataList.size()) {
                    list.add(dataList.get(j));
                }
            }
            ExtraMenuAdapter gridViewAdapter = new ExtraMenuAdapter(getContext(), list, itemHeight, squareWidth);
            grid_view.setAdapter(gridViewAdapter);
            grid_view.setNumColumns(pageCount / 2);
            viewList.add(priPhotoView);
        }
        viewPagerAdapter = new ViewPagerAdapter(viewList);
        horizontalScrollMenu.setAdapter(viewPagerAdapter);
        CircleNavigator circleNavigator = new CircleNavigator(getContext());
        circleNavigator.setCircleCount(pageNum);
        circleNavigator.setCircleColor(getContext().getResources().getColor(R.color.extraMenuMagicIndicatorBack));
        circleNavigator.setRadius(8);
        circleNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                horizontalScrollMenu.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, horizontalScrollMenu);
    }


    public void add(List<ExtraMenu> menus) {
        dataList = menus;
        layoutExtraMenu();
    }

}
