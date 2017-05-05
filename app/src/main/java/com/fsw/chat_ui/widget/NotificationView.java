package com.fsw.chat_ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fsw.chat_ui.utils.DisplayUtil;

/**
 * Created by Admin on 2017/4/11.
 */

public class NotificationView extends View {

    private int width, height;

    public NotificationView(Context context) {
        this(context, null);
    }

    public NotificationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        width = DisplayUtil.getDisplayMetrics(context).widthPixels;
        height = DisplayUtil.getNotificationBarHeight((Activity) context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }
}
