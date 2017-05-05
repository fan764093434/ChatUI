package com.fsw.chat_ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fsw.chat_ui.R;

/**
 * Created by Admin on 2017/3/27.
 */

public class SendMessageTip extends LinearLayout {

    private Animation animation;

    private ImageView tipImage;

    public SendMessageTip(Context context) {
        this(context, null);
    }

    public SendMessageTip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendMessageTip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_send_message_tip, this);
        tipImage = (ImageView) findViewById(R.id.tip_image);
        animation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(2000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        start();
    }

    public void release() {
        tipImage.setAnimation(null);
    }

    public void start() {
        tipImage.setAnimation(animation);
    }

}
