package com.fsw.chat_ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsw.chat_ui.R;


/**
 * Created by fsw on 2017/3/6.
 * use 自定义导航栏
 */

public class ProjectToolbar extends RelativeLayout {

    protected Context context;
    /**
     * navigationIcon
     */
    protected ImageView navigationIcon;
    /**
     * 最大长度为6
     * 用于展示一些数量
     * 比如未读消息总数
     */
    protected TextView unreadMum;
    /**
     * 标题
     * 最大长度为12
     * 用于展示标题
     */
    protected TextView title;

    protected View centerContent;


    protected TextView centerTitle;
    /**
     * 副标题
     * 最大长度为20
     * 用于展示一些描述
     */
    protected TextView subtitle;
    /**
     * 菜单按钮一
     */
    protected ImageView menuOne;
    /**
     * 菜单按钮二
     */
    protected ImageView menuTwo;


    public ProjectToolbar(Context context) {
        this(context, null);
    }

    public ProjectToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProjectToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化View
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.chat_view_toolbar, this);
        navigationIcon = (ImageView) findViewById(R.id.navigation_icon);
        unreadMum = (TextView) findViewById(R.id.unread_mum);
        title = (TextView) findViewById(R.id.title);
        subtitle = (TextView) findViewById(R.id.subtitle);
        menuOne = (ImageView) findViewById(R.id.menu_one);
        menuTwo = (ImageView) findViewById(R.id.menu_two);
        centerContent = findViewById(R.id.center_content);
        centerTitle = (TextView) findViewById(R.id.center_title);
    }

    /**
     * 设置Navigation图标
     *
     * @param drawable
     */
    public void setNavigationIcon(int drawable) {
        navigationIcon.setImageDrawable(context.getResources().getDrawable(drawable));
    }

    /**
     * 设置Navigation显示还是隐藏
     * 默认为显示
     *
     * @param var0
     */
    public void setNavigationIconVisitable(boolean var0) {
        if (var0) {
            navigationIcon.setVisibility(View.VISIBLE);
        } else {
            navigationIcon.setVisibility(View.GONE);
        }
    }

    /**
     * 设置NavigationIcon的点击事件
     */
    public int getNavigationIconId() {
        return navigationIcon.getId();
    }

    /**
     * 设置NavigationIcon的点击事件
     */
    public View getNavigationIcon() {
        return navigationIcon;
    }

    /**
     * 设置标题
     *
     * @param var0
     */
    public void setTitle(String var0) {
        if (!TextUtils.isEmpty(var0)) {
            title.setText(var0);
            title.setVisibility(VISIBLE);
        } else {
            title.setVisibility(GONE);
        }
    }

    /**
     * 设置未读消息总数
     *
     * @param var0
     */
    public void setUnreadMum(String var0) {
        if (!TextUtils.isEmpty(var0)) {
            unreadMum.setText(var0);
            unreadMum.setVisibility(VISIBLE);
        } else {
            unreadMum.setVisibility(GONE);
        }
    }

    /**
     * 设置中间大标题
     *
     * @param var0
     */
    public void setCenterTitle(String var0) {
        if (!TextUtils.isEmpty(var0)) {
            centerTitle.setText(var0);
            centerTitle.setVisibility(VISIBLE);
        } else {
            centerTitle.setVisibility(GONE);
        }
    }

    /**
     * 设置小标题
     *
     * @param var0
     */
    public void setSubtitle(String var0) {
        if (!TextUtils.isEmpty(var0)) {
            subtitle.setText(var0);
            subtitle.setVisibility(VISIBLE);
        } else {
            subtitle.setVisibility(GONE);
        }
    }


    public void centerContentVisible(boolean var0) {
        if (var0) {
            centerContent.setVisibility(View.VISIBLE);
        } else {
            centerContent.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 设置菜单一图标
     *
     * @param drawable
     */
    public void setMenuOneIcon(int drawable) {
        menuOne.setImageDrawable(context.getResources().getDrawable(drawable));
    }

    /**
     * 设置菜单二显示还是隐藏
     * 默认为显示
     *
     * @param var0
     */
    public void setMenuTwoVisitable(boolean var0) {
        if (var0) {
            menuTwo.setVisibility(View.VISIBLE);
        } else {
            menuTwo.setVisibility(View.GONE);
        }
    }

    /**
     * 设置菜单二图标
     *
     * @param drawable
     */
    public void setMenuTwoIcon(int drawable) {
        menuTwo.setImageDrawable(context.getResources().getDrawable(drawable));
    }

    /**
     * 设置菜单一显示还是隐藏
     * 默认为显示
     *
     * @param var0
     */
    public void setMenuOneVisitable(boolean var0) {
        if (var0) {
            menuOne.setVisibility(View.VISIBLE);
        } else {
            menuOne.setVisibility(View.GONE);
        }
    }


    public void setMenuOneOnClickListener(OnClickListener listener) {
        menuOne.setOnClickListener(listener);
    }

    public void setMenuTwoOnClickListener(OnClickListener listener) {
        menuTwo.setOnClickListener(listener);
    }

}
