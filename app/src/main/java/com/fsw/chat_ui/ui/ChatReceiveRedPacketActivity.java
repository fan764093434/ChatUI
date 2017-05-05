package com.fsw.chat_ui.ui;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.widget.flake.FlakeView;

/**
 * Created by Admin on 2017/3/28.
 */

public class ChatReceiveRedPacketActivity extends BaseActivity {


    private FlakeView flakeView;
    private MediaPlayer player;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_receive_red_packet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main);
        flakeView = new FlakeView(this);
        frameLayout.addView(flakeView);
        //设置同时出现在屏幕上的金币数量  建议64以内 过多会引起卡顿
        flakeView.addFlakes(8);
        /**
         * 绘制的类型
         * @see View.LAYER_TYPE_HARDWARE
         * @see View.LAYER_TYPE_SOFTWARE
         * @see View.LAYER_TYPE_NONE
         */
        flakeView.setLayerType(View.LAYER_TYPE_NONE, null);
        player = MediaPlayer.create(this, R.raw.shake_two);
        player.start();
        /**
         * 移除动画
         */
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置2秒后
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            frameLayout.removeView(flakeView);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        flakeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flakeView.pause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();//释放媒体播放对象所占用的资源
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_receive_red_packet_enter, R.anim.activity_receive_red_packet_exit);
    }
}
