package com.fsw.chat_ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.fsw.chat_ui.R;

/**
 * Created by Admin on 2017/4/7.
 */

public class DragView extends RelativeLayout {

    private int startLeft;
    private int startTop;
    private int startRight;
    private int startBottom;

    private float startY;//down事件发生时，手指相对于view左上角y轴的距离
    private int left;//DragTV左边缘相对于父控件的距离
    private int top;//DragTV上边缘相对于父控件的距离
    private int right;//DragTV右边缘相对于父控件的距离
    private int bottom;//DragTV底边缘相对于父控件的距离
    private int ver;//触摸情况下，手指在y轴方向移动的距离
    private int slideDistance = 0;
    private int height = 0;
    private boolean isMeasure = false;
    private onDragListener onDragListener;

    public void setOnDragListener(DragView.onDragListener onDragListener) {
        this.onDragListener = onDragListener;
    }

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight = measureLength(heightMeasureSpec);
        if (!isMeasure) {
            height = measuredHeight;
            isMeasure = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureLength(int measureSpec) {
        int specModel = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specModel == MeasureSpec.EXACTLY || specModel == MeasureSpec.AT_MOST) {
            result = specSize;
        }
        return result;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 手指刚触摸到屏幕的那一刻，手指相对于View左上角水平和竖直方向的距离:startX startY
                startY = event.getY();
                startLeft = getLeft();
                startTop = getTop();
                startRight = getRight();
                startBottom = getBottom();
                if (onDragListener != null) {
                    onDragListener.onDragStart();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指停留在屏幕或移动时，手指相对与View左上角水平和竖直方向的距离:endX endY
                // 获取此时刻 View的位置。
                left = getLeft();
                top = getTop();
                right = getRight();
                bottom = getBottom();
                // 手指移动的竖直距离
                ver = (int) (event.getY() - startY);
                // 当手指在水平或竖直方向上发生移动时，重新设置View的位置（layout方法）
                slideDistance = startTop - top;
                if (ver != 0) {
                    layout(left, top + ver, right, bottom + ver);
                    if (onDragListener != null) {
                        onDragListener.onDrag(slideDistance);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                layout(startLeft, startTop, startRight, startBottom);
                if (onDragListener != null) {
                    onDragListener.onDragStop();
                }
                if (slideDistance >= height) {
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.activity_receive_red_packet_enter);
                    this.setAnimation(animation);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public interface onDragListener {
        void onDragStart();

        void onDrag(int ver);

        void onDragStop();
    }

}
