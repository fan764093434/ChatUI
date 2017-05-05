package com.fsw.chat_ui.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import com.fsw.chat_ui.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Admin on 2017/4/10.
 */

public class ChatVideoPlayActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    @InjectView(R.id.video_view)
    VideoView videoView;

    private String videoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_video_play);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        videoUrl = getIntent().getStringExtra("videoUrl");
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.start();
        videoView.setOnTouchListener(this);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_receive_red_packet_enter, R.anim.activity_receive_red_packet_exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (v.getId()) {
            case R.id.video_view:
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
        return true;
    }
}
