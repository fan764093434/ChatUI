package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatFileViewHolder;
import com.fsw.chat_ui.entity.Message;

/**
 * Created by Admin on 2017/4/8.
 */

public class ChatRowFile extends ChatRow {

    private ChatFileViewHolder viewHolder;

    public ChatRowFile(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_file_receive : R.layout.chat_row_file_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatFileViewHolder();
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatFileViewHolder) this.getTag();
        }
    }

    @Override
    protected void onSetUpView() {

    }

    @Override
    protected void onSetClickListener() {

    }
}
