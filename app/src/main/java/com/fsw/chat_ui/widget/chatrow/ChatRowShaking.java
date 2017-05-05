package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.Message;

/**
 * Created by fsw on 2017/4/12.
 * 抖一抖消息行
 */

public class ChatRowShaking extends ChatRow implements View.OnClickListener {

    private View bubble;

    public ChatRowShaking(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_shake_receive : R.layout.chat_row_shake_send, this);
    }

    @Override
    protected void onViewFindById() {
        bubble = findViewById(R.id.bubble);
    }

    @Override
    protected void onSetUpView() {

    }

    @Override
    protected void onSetClickListener() {
        bubble.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bubble:
                itemClickListener.onBubbleClick(message);
                break;
        }
    }
}
