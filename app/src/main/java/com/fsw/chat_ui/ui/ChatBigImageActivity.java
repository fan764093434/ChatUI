package com.fsw.chat_ui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.fsw.chat_ui.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Admin on 2017/4/10.
 */

public class ChatBigImageActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 支持缩放的ImageView
     */
    @InjectView(R.id.image)
    PhotoView image;
    /**
     * 图片路径
     */
    private String imagePath;

    private boolean isHidden = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_big_image);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        //获取图片路径
        imagePath = getIntent().getStringExtra("imagePath");
        //Glide加载图片
        Glide.with(this)
                .load(imagePath)
                .placeholder(R.mipmap.default_image)
                .into(image);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_receive_red_packet_enter, R.anim.activity_receive_red_packet_exit);
    }

    @OnClick({R.id.close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                finish();
//                isHidden = !isHidden;
//                full(isHidden);
                break;
        }
    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
