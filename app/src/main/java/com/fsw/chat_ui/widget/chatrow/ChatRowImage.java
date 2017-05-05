package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatImageViewHolder;
import com.fsw.chat_ui.entity.Message;
import com.fsw.chat_ui.ui.ChatBigImageActivity;

/**
 * Created by Admin on 2017/3/27.
 */

public class ChatRowImage extends ChatRow implements View.OnClickListener {

    private ChatImageViewHolder viewHolder;

    private String imagePath;

    public ChatRowImage(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_image_receive : R.layout.chat_row_image_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatImageViewHolder();
            viewHolder.bubble = (FrameLayout) findViewById(R.id.bubble);
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatImageViewHolder) this.getTag();
        }
    }

    @Override
    protected void onSetUpView() {
        imagePath = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/040613593118.jpg";
    }

    @Override
    protected void onSetClickListener() {
        viewHolder.bubble.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bubble:
                Intent intent = new Intent(activity, ChatBigImageActivity.class);
                intent.putExtra("imagePath", imagePath);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_receive_red_packet_enter, R.anim.activity_receive_red_packet_exit);
                break;
        }
    }
}
