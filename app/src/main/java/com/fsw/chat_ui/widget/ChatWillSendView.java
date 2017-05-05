package com.fsw.chat_ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fsw.chat_ui.utils.DisplayUtil;

/**
 * Created by Admin on 2017/4/7.
 */

public class ChatWillSendView extends ImageView {

    private RelativeLayout.LayoutParams params;
    private int left, width, top, height;

    private DisplayMetrics displayMetrics;

    private int notificationBarHeight;

    public ChatWillSendView(Context context) {
        this(context, null);
    }

    public ChatWillSendView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatWillSendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        //获取屏幕的高度
        displayMetrics = DisplayUtil.getDisplayMetrics(context);
        notificationBarHeight = DisplayUtil.getNotificationBarHeight((Activity) context);
    }

    public void setStartParams(ImageView image, int w, int y) {
        width = w;
        height = y;
        int[] location = new int[2];
        image.getLocationInWindow(location);
        left = location[0];
        top = location[1];
        params = new RelativeLayout.LayoutParams(width, height);
        image.buildDrawingCache();
        image.setImageBitmap(image.getDrawingCache());
        setLayoutParams(params);
        params.setMargins(left, top - notificationBarHeight, displayMetrics.widthPixels - (left + width), displayMetrics.heightPixels - (top + height));
        setLayoutParams(params);
    }

    public void dragParams(int ver) {
        params.setMargins(left, top + ver - notificationBarHeight, displayMetrics.widthPixels - (left + width), displayMetrics.heightPixels - (top + height) - ver);
        setLayoutParams(params);
    }

}
