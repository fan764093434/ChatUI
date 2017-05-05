package com.fsw.chat_ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.fsw.chat_ui.R;

/**
 * Created by Admin on 2017/4/8.
 */

public class CircleRelativeLayout extends RelativeLayout {
    /**
     * 默认值
     */
    private float roundLayoutRadius = 0;

    /**
     * 方形path和RectF
     */
    private Path roundPath;
    private RectF rectF;

    public CircleRelativeLayout(Context context) {
        this(context, null);
    }

    public CircleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout);
        roundLayoutRadius = typedArray.getDimension(R.styleable.RoundLayout_roundLayoutRadius, roundLayoutRadius);
        typedArray.recycle();
        init();
    }

    private void init() {
        setWillNotDraw(false);//如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的;
        roundPath = new Path();
        rectF = new RectF();
    }

    private void setRoundPath() {
        //添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
        roundPath.addRoundRect(rectF, dp2px(roundLayoutRadius), dp2px(roundLayoutRadius), Path.Direction.CW);
    }

    /**
     * @param roundLayoutRadius
     */
    public void setRoundLayoutRadius(float roundLayoutRadius) {
        this.roundLayoutRadius = roundLayoutRadius;
        setRoundPath();
        postInvalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        rectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();
    }


    @Override
    public void draw(Canvas canvas) {
        if (roundLayoutRadius > 0f) {
            canvas.clipPath(roundPath);
        }
        super.draw(canvas);
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public int dp2px(float dipValue) {
        return (int) (dipValue * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

}
