package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatRedPacketViewHolder;
import com.fsw.chat_ui.entity.Message;
import com.fsw.chat_ui.ui.ChatReceiveRedPacketActivity;
import com.fsw.chat_ui.ui.ChatRedPacketStatusActivity;

/**
 * Created by Admin on 2017/3/28.
 */

public class ChatRowRedPacket extends ChatRow implements View.OnClickListener {

    private ChatRedPacketViewHolder viewHolder;

    public ChatRowRedPacket(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_red_packet_receive : R.layout.chat_row_red_packet_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatRedPacketViewHolder();
            viewHolder.openRedPacket = (ImageView) findViewById(R.id.open_red_packet);
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatRedPacketViewHolder) this.getTag();
        }
    }

    @Override
    protected void onSetUpView() {

    }

    @Override
    protected void onSetClickListener() {
        if (viewHolder.openRedPacket != null) {
            viewHolder.openRedPacket.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_red_packet:
                Intent intent;
                if (message.getDirect() == ChatMessage.Direct.RECEIVE) {
                    intent = new Intent(activity, ChatReceiveRedPacketActivity.class);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.activity_receive_red_packet_enter, R.anim.activity_receive_red_packet_exit);
                } else {
                    intent = new Intent(activity, ChatRedPacketStatusActivity.class);
                    activity.startActivity(intent);
                }
                break;
        }
    }

}
