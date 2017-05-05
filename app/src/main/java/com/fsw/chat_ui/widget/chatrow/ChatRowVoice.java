package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatVoiceViewHolder;
import com.fsw.chat_ui.entity.Message;

/**
 * Created by Admin on 2017/4/8.
 */

public class ChatRowVoice extends ChatRow implements View.OnClickListener {

    private ChatVoiceViewHolder viewHolder;

    public ChatRowVoice(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_voice_receive : R.layout.chat_row_voice_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatVoiceViewHolder();

            viewHolder.iv_voice = (ImageView) findViewById(R.id.iv_voice);
            viewHolder.bubble = (LinearLayout) findViewById(R.id.bubble);
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatVoiceViewHolder) this.getTag();
        }
    }

    @Override
    protected void onSetUpView() {

    }

    @Override
    protected void onSetClickListener() {
        viewHolder.bubble.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bubble:
                AnimationDrawable voiceAnimation;
                if (message.getDirect() == ChatMessage.Direct.RECEIVE) {
                    viewHolder.iv_voice.setImageResource(R.drawable.chat_voice_receive_icon);
                } else {
                    viewHolder.iv_voice.setImageResource(R.drawable.chat_voice_send_icon);
                }
                voiceAnimation = (AnimationDrawable) viewHolder.iv_voice.getDrawable();
                voiceAnimation.start();
                break;
        }
    }
}
