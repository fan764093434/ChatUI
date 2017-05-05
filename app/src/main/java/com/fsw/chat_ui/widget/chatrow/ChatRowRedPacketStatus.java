package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatRedPacketStatusViewHolder;
import com.fsw.chat_ui.entity.Message;

/**
 * Created by Admin on 2017/3/28.
 */

public class ChatRowRedPacketStatus extends ChatRow {

    private ChatRedPacketStatusViewHolder viewHolder;

    public ChatRowRedPacketStatus(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_red_packet_status_receive : R.layout.chat_row_red_packet_status_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatRedPacketStatusViewHolder();
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatRedPacketStatusViewHolder) this.getTag();
        }
    }

    @Override
    protected void onSetUpView() {
    }

    @Override
    protected void onSetClickListener() {

    }


}
