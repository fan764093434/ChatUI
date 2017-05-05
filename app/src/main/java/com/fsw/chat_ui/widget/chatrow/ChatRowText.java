package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatTextViewHolder;
import com.fsw.chat_ui.entity.Message;

/**
 * Created by fsw on 2017/3/27.
 * 文本类型的消息
 */

public class ChatRowText extends ChatRow {

    private ChatTextViewHolder viewHolder;

    public ChatRowText(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_text_receive : R.layout.chat_row_text_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatTextViewHolder();
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatTextViewHolder) this.getTag();
        }
    }

    @Override
    protected void onSetUpView() {

    }

    @Override
    protected void onSetClickListener() {

    }
}
