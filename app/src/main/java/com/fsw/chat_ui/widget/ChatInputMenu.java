package com.fsw.chat_ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.Emotion;

import java.lang.reflect.Field;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatInputMenu extends LinearLayout implements View.OnClickListener {
    /**
     * 更多菜单栏张开按钮
     */
    private ImageView openOperateMenuBtn;
    /**
     * 聊天内容输入框
     */
    private EditText messageInput;
    /**
     * 发送语音提示文字
     */
    private TextView voiceTitle;
    /**
     * 消息发送按钮
     */
    private ImageView sendButton;
    /**
     * 输入区域
     */
    private LinearLayout inputView;

    private SiriWaveViewNine siriWaveViewNine;

    private OnOperateOpenButtonClickListener listener;

    public void setOnOperateOpenButtonClickListener(OnOperateOpenButtonClickListener listener) {
        this.listener = listener;
    }

    public ChatInputMenu(Context context) {
        this(context, null);
    }

    public ChatInputMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatInputMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_view_input_menu, this);
        openOperateMenuBtn = (ImageView) findViewById(R.id.open_operate_menu);
        messageInput = (EditText) findViewById(R.id.message_input);
        voiceTitle = (TextView) findViewById(R.id.voice_title);
        sendButton = (ImageView) findViewById(R.id.send_button);
        inputView = (LinearLayout) findViewById(R.id.input_view);
        openOperateMenuBtn.setOnClickListener(this);
        messageInput.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        voiceTitle.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int x = 0, y = 0;
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        voiceTitle.setText("松开结束录音");
                        if (siriWaveViewNine != null) {
                            siriWaveViewNine.setVisibility(View.VISIBLE);
                        }
                        x = (int) event.getX();
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(event.getY() - y) > 100) {
                            voiceTitle.setText("上滑取消发送");
                        } else {
                            voiceTitle.setText("松开结束录音");
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (siriWaveViewNine != null) {
                            siriWaveViewNine.setVisibility(View.GONE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (siriWaveViewNine != null) {
                            siriWaveViewNine.setVisibility(View.GONE);
                        }
                        voiceTitle.setText("按住这里开始说话");
                        break;
                }
                return true;
            }
        });
    }

    public void registerSiriWavaView(SiriWaveViewNine siriWaveViewNine) {
        this.siriWaveViewNine = siriWaveViewNine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_operate_menu:
                if (listener != null) {
                    listener.onOperateOpenButtonClickListener(openOperateMenuBtn);
                }
                break;
            case R.id.message_input:
                if (listener != null) {
                    listener.onInputClickListener(openOperateMenuBtn);
                }
                break;
            case R.id.send_button:

                break;
        }
    }

    /**
     * 表情输入
     *
     * @param emotion
     */
    public void addTextToMessageView(Emotion emotion) {
        if (messageInput != null) {
            int cursor = messageInput.getSelectionStart();
            Field f;
            try {
                f = R.drawable.class.getDeclaredField(emotion.getName());
                int j = f.getInt(R.drawable.class);
                Drawable d = getResources().getDrawable(j);
                int textSize = (int) messageInput.getTextSize();
                d.setBounds(0, 0, textSize, textSize);
                SpannableString ss = new SpannableString(emotion.getName());
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                ss.setSpan(span, 0, emotion.getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                messageInput.getText().insert(cursor, ss);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示发送语音区域
     */
    protected void showSendVoiceView(ImageView imageView) {
        if (messageInput.getVisibility() == VISIBLE) {
            imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_microphone_pressed));
            messageInput.setVisibility(View.GONE);
            voiceTitle.setVisibility(View.VISIBLE);
            inputView.setBackground(getResources().getDrawable(R.drawable.chat_input_round_view_back_voice));
        } else {
            imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_microphone_normal));
            messageInput.setVisibility(View.VISIBLE);
            voiceTitle.setVisibility(View.GONE);
            inputView.setBackground(getResources().getDrawable(R.drawable.chat_input_round_view_back));
        }
    }

    protected void resetPage() {
        messageInput.setVisibility(View.VISIBLE);
        voiceTitle.setVisibility(View.GONE);
        inputView.setBackground(getResources().getDrawable(R.drawable.chat_input_round_view_back));
    }

    public interface OnOperateOpenButtonClickListener {
        /**
         * @param openOperateMenuBtn
         */
        void onOperateOpenButtonClickListener(ImageView openOperateMenuBtn);

        /**
         * 当输入框点击事件
         */
        void onInputClickListener(ImageView openOperateMenuBtn);
    }
}
