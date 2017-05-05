package com.fsw.chat_ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by fsw on 2017/4/7.
 * 为了减小横向滚动的灵敏度
 */

public class LowSensitiveRecyclerView extends RecyclerView {

    private float xDistance, yDistance, xLast, yLast;

    public LowSensitiveRecyclerView(Context context) {
        super(context);
    }

    public LowSensitiveRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LowSensitiveRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (yDistance > xDistance) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

}
