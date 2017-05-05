package com.fsw.chat_ui.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.utils.DialogHelper;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Admin on 2017/4/4.
 */

public class ChatOperatePhotoActivity extends BaseActivity {

    @InjectView(R.id.image)
    ImageView image;

    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_preview_photo);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        path = getIntent().getStringExtra("path");
        final Dialog dialog = DialogHelper.compressImageTip(this);
        Luban.get(this)
                .load(new File(path))//传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)//设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        image.setImageBitmap(bitmap);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(ChatOperatePhotoActivity.this, "处理失败", Toast.LENGTH_SHORT).show();
                    }
                })//设置回调
                .launch();//启动压缩

    }

    @OnClick({R.id.close})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                finish();
                break;
        }
    }

}
