package com.fsw.chat_ui.widget.chatrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.viewholder.ChatVideoViewHolder;
import com.fsw.chat_ui.entity.Message;
import com.fsw.chat_ui.ui.ChatVideoPlayActivity;
import com.fsw.chat_ui.utils.FileHelper;

import java.io.File;

/**
 * Created by Admin on 2017/4/8.
 */

public class ChatRowVideo extends ChatRow implements View.OnClickListener {

    private ChatVideoViewHolder viewHolder;

    private String videoUrl;

    public ChatRowVideo(Context context, Message message) {
        super(context, message);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.getDirect() == ChatMessage.Direct.RECEIVE ? R.layout.chat_row_video_receive : R.layout.chat_row_video_send, this);
    }

    @Override
    protected void onViewFindById() {
        if (viewHolder == null) {
            viewHolder = new ChatVideoViewHolder();
            viewHolder.videoImage = (ImageView) findViewById(R.id.video_image);
            viewHolder.startPlay = (ImageView) findViewById(R.id.start_play);
            this.setTag(viewHolder);
        } else {
            viewHolder = (ChatVideoViewHolder) this.getTag();
        }

    }

    @Override
    protected void onSetUpView() {//040616562067
        videoUrl = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath() + "/dd.mp4";
        if (viewHolder == null) {
            throw new NullPointerException("viewHolder is null");
        }
        if (viewHolder.videoImage != null) {
            setBitmap(viewHolder.videoImage, videoUrl);
        }
    }

    @Override
    protected void onSetClickListener() {
        if (viewHolder.startPlay != null) {
            viewHolder.startPlay.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_play:
                Intent intent = new Intent(activity, ChatVideoPlayActivity.class);
                intent.putExtra("videoUrl", videoUrl);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_receive_red_packet_enter, R.anim.activity_receive_red_packet_exit);
                break;
        }
    }

    /**
     * 异步加载视频缩略图
     *
     * @param iv
     * @param videoUrl
     */
    private void setBitmap(final ImageView iv, final String videoUrl) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                if (new File(videoUrl).exists()) {
                    return FileHelper.getVideoFirstFrame(videoUrl);
                } else {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (result != null) {
                    iv.setImageBitmap(result);
                } else {

                }
            }
        }.execute();
    }

}
