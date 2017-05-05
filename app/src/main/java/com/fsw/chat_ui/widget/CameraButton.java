package com.fsw.chat_ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 2017/3/31.
 */

public class CameraButton extends View {

    private Paint paint;

    /**
     * 中心圆背景色
     */
    private int centerColor = 0xff7cadff;
    /**
     * 字体大小
     */
    private int textSize = 25;
    /**
     * 文字容器
     */
    private Rect textRect;
    private String textTitle = "点击拍照，长按录像";
    private int btnWidth;
    /**
     * 外面进度条的宽度
     */
    private int progressWidth = 10;
    /**
     * 中间空白的宽度
     */
    private int intervalWidth = 15;
    /**
     * 长按时扩大的像素
     */
    private int expandPixels = 10;
    /**
     * 中心按钮的大小
     */
    private int centerRadius = 60;
    /**
     * 文字与按钮的距离
     */
    private int textDistanceWithBtn = 10;
    private int centerX, centerY;
    private int maxWidth, maxHeight;
    /**
     * 最外层RectF
     */
    private RectF rectF;
    /**
     * 当前的进度
     */
    private float currentProgress = 0;
    /**
     * 设置最大的进度
     */
    private float maxProgress = 100;
    /**
     * 是否为拍摄视频
     */
    private boolean isTakeVideo = false;

    private boolean keyUp = false;
    /**
     * 按下多久后触发拍摄视频事件
     * 默认为500毫秒
     */
    private final int keyDownForTakeVideo = 500;
    /**
     * 使用Timer ,TimerTask 和 Handler三者相结合的方法更新滚动条
     */
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (currentProgress > maxProgress) {
                        if (onButtonClickListener != null) {
                            onButtonClickListener.stopRecord();
                        }
                        textTitle = "录像完毕";
                    } else {
                        currentProgress++;
                        textTitle = currentProgress / 10 + "秒";
                    }
                    invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (isTakeVideo) {//如果是在播放才执行任务
                handler.sendEmptyMessage(0);
            }
        }
    }

    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public CameraButton(Context context) {
        this(context, null);
    }

    public CameraButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        //底部按钮的宽度
        btnWidth = (progressWidth + intervalWidth + centerRadius + expandPixels) * 2;
        //文字区域的宽度
        int textWidth = textSize * textTitle.length();
        maxWidth = Math.max(btnWidth, textWidth);
        //控件的总高度
        maxHeight = btnWidth + textSize + textDistanceWithBtn;
        textRect = new Rect(0, 0, maxWidth, textSize);
        centerX = maxWidth / 2;
        centerY = btnWidth / 2 + textSize + textDistanceWithBtn;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(maxWidth, maxHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //写文字
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(textTitle, textRect.centerX(), textRect.centerY() + textSize / 2, paint);
        //画中心圆
        paint.setColor(centerColor);
        paint.setStyle(Paint.Style.FILL);
        if (isTakeVideo) {//如果为拍摄视频放大按钮
            canvas.drawCircle(centerX, centerY, centerRadius, paint);
            rectF = new RectF(centerX - btnWidth / 2 + progressWidth, textSize + progressWidth + textDistanceWithBtn, centerX + btnWidth / 2 - progressWidth, centerY + btnWidth / 2 - progressWidth);
        } else {
            canvas.drawCircle(centerX, centerY, centerRadius - expandPixels, paint);
            rectF = new RectF(centerX - btnWidth / 2 + progressWidth + expandPixels, textSize + progressWidth + expandPixels + textDistanceWithBtn, centerX + btnWidth / 2 - progressWidth - expandPixels, centerY + btnWidth / 2 - progressWidth - expandPixels);
        }
        //绘制进度条底色
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(progressWidth);
        canvas.drawArc(rectF, -90, 360, false, paint);
        //绘制进度条
        paint.setColor(0xff46a287);
        canvas.drawArc(rectF, -90, (currentProgress / maxProgress) * 360, false, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                keyUp = false;
                centerColor = 0xff3070b7;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!keyUp) {
                            isTakeVideo = true;
                            timer = new Timer();
                            timerTask = new MyTimerTask();
                            timer.schedule(timerTask, 0, 100);
                            if (onButtonClickListener != null) {
                                onButtonClickListener.startRecord();
                            }
                            invalidate();
                        }
                    }
                }, keyDownForTakeVideo);
                break;
            case MotionEvent.ACTION_UP:
                keyUp = true;
                textTitle = "点击拍照,长按录像";
                centerColor = 0xff7cadff;
                timerTask = null;
                if (timer != null) {
                    timer.cancel();
                }
                timer = null;
                if (isTakeVideo) {//录视频
                    currentProgress = 0;
                    isTakeVideo = false;
                    if (onButtonClickListener != null) {
                        onButtonClickListener.stopRecord();
                    }
                } else {//拍照
                    if (onButtonClickListener != null) {
                        onButtonClickListener.takePhoto();
                    }
                }
                isTakeVideo = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        invalidate();
        return true;
    }

    public interface OnButtonClickListener {
        /**
         * 拍照
         */
        void takePhoto();

        /**
         * 开始录制
         */
        void startRecord();

        /**
         * 结束录制
         */
        void stopRecord();
    }

}
