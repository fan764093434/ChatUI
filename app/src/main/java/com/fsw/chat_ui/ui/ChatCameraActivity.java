package com.fsw.chat_ui.ui;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.utils.CameraHelper;
import com.fsw.chat_ui.utils.DisplayUtil;
import com.fsw.chat_ui.widget.CameraButton;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by fsw on 2017/3/31.
 * use 拍摄照片或者视频
 */

public class ChatCameraActivity extends BaseActivity implements View.OnTouchListener {
    /**
     * 关闭按钮
     */
    @InjectView(R.id.close)
    ImageView close;
    /**
     * 调焦的指示
     */
    @InjectView(R.id.camera_focus)
    View cameraFocus;
    /**
     * 相机成像的区域
     */
    @InjectView(R.id.camera_surface_view)
    SurfaceView cameraView;
    /**
     * 拍照按钮
     */
    @InjectView(R.id.camera_button)
    CameraButton cameraButton;
    /**
     * 闪光灯
     */
    @InjectView(R.id.light)
    ImageView light;
    /**
     * 更换摄像头
     */
    @InjectView(R.id.change)
    ImageView change;
    /**
     * 修改
     */
    @InjectView(R.id.edit_setting)
    ImageView editSetting;
    /**
     * 更多功能
     */
    @InjectView(R.id.more_setting)
    ImageView moreSetting;
    /**
     * 底部按钮背景
     */
    @InjectView(R.id.footer_view)
    View footerView;

    private int cameraFocusWidth;
    private int cameraFocusHeight;
    /**
     * 屏幕的高款
     */
    private DisplayMetrics displayMetrics;

    /**
     * 记录手势开始的地方
     */
    private double nLenStart = 0;
    /**
     * 焦距
     */
    private int zoom = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_take_photo_or_video);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        //获取屏幕的高宽
        displayMetrics = DisplayUtil.getDisplayMetrics(this);
        //进入该页面后应保持屏幕常亮，亮度不变
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //给预览界面设置触摸事件
        cameraView.setOnTouchListener(this);
        //测量聚焦控件视图的高宽
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        cameraFocus.measure(width, height);
        cameraFocusHeight = DisplayUtil.dp2px(cameraFocus.getMeasuredHeight());
        cameraFocusWidth = DisplayUtil.dp2px(cameraFocus.getMeasuredWidth());
        //拍照按钮的点击事件
        cameraButton.setOnButtonClickListener(new CameraButton.OnButtonClickListener() {
            @Override
            public void takePhoto() {
                CameraHelper.getInstance().takePhoto(new CameraHelper.OnPictureSaveFinishListener() {
                    @Override
                    public void onFinish(String path) {
                        Intent intent = new Intent(ChatCameraActivity.this, ChatOperatePhotoActivity.class);
                        intent.putExtra("path", path);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void startRecord() {
                hiddenBtnGroup();
                CameraHelper.getInstance().recordVideo();
            }

            @Override
            public void stopRecord() {
                showBtnGroup();
                CameraHelper.getInstance().stopRecord();
            }
        });

        //camera的基本设置
        CameraHelper
                .getInstance()
                .with(this)
                .getCamera()
                .setHolder(cameraView.getHolder());
        if (CameraHelper.getInstance().getFlashMode() == Camera.Parameters.FLASH_MODE_AUTO) {
            light.setImageDrawable(this.getResources().getDrawable(R.drawable.chat_icon_flash_lamp_auto));
        } else if (CameraHelper.getInstance().getFlashMode() == Camera.Parameters.FLASH_MODE_TORCH) {
            light.setImageDrawable(this.getResources().getDrawable(R.drawable.chat_icon_flash_lamp_open));
        } else {
            light.setImageDrawable(this.getResources().getDrawable(R.drawable.chat_icon_flash_lamp_close));
        }
    }

    @OnClick({R.id.change, R.id.close, R.id.edit_setting, R.id.more_setting, R.id.light})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.change:
                try {
                    CameraHelper.getInstance().changeCamera();
                } catch (IOException e) {
                    Toast.makeText(this, "相机初始化异常", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.edit_setting:
                break;
            case R.id.more_setting:
                break;
            case R.id.light:
                CameraHelper.getInstance().setLightMode(light);
                break;
            default:

                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.camera_surface_view) {
            int pointCount = event.getPointerCount();
            int action = event.getAction();
            if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == pointCount) {//2表示两个手指
                cameraFocus.setVisibility(View.GONE);
                int xLen = Math.abs((int) event.getX(0) - (int) event.getX(1));
                int yLen = Math.abs((int) event.getY(0) - (int) event.getY(1));
                //求手势两点间的距离
                nLenStart = Math.sqrt((double) xLen * xLen + (double) yLen * yLen);
            } else if ((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE && 2 == pointCount) {
                int xLen = Math.abs((int) event.getX(0) - (int) event.getX(1));
                int yLen = Math.abs((int) event.getY(0) - (int) event.getY(1));
                //求手势两点间的距离
                double nLenEnd = Math.sqrt((double) xLen * xLen + (double) yLen * yLen);
                if (nLenStart < nLenEnd) {//放大
                    if (Math.abs(nLenStart - nLenEnd) > 30) {
                        zoom++;
                        if (zoom > CameraHelper.getInstance().getMaxZoom() * CameraHelper.multiple) {
                            zoom = CameraHelper.getInstance().getMaxZoom() * CameraHelper.multiple;
                        }
                    }
                } else {//缩小
                    if (Math.abs(nLenStart - nLenEnd) > 30) {
                        if (zoom != 0) {
                            zoom--;
                        }
                    }
                }
                CameraHelper.getInstance().zoom(zoom);
            } else if (action == MotionEvent.ACTION_UP) {
                cameraFocus.setVisibility(View.GONE);
            } else if (action == MotionEvent.ACTION_DOWN) {
                float x = event.getX();
                float y = event.getY();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cameraFocus.getLayoutParams();
                params.leftMargin = (int) (x - cameraFocusWidth / 2);
                params.topMargin = (int) (y - cameraFocusHeight / 2);
                params.rightMargin = (int) (displayMetrics.widthPixels - x - cameraFocusWidth / 2);
                params.bottomMargin = (int) (displayMetrics.heightPixels - y - cameraFocusHeight / 2);
                cameraFocus.setLayoutParams(params);
                cameraFocus.setVisibility(View.VISIBLE);
                CameraHelper.getInstance().focusOnTouch(cameraView, event);
            }
            return true;
        }
        return false;
    }

    /**
     * 当录像的时候隐藏按钮群
     */
    private void hiddenBtnGroup() {
        footerView.setBackgroundColor(this.getResources().getColor(R.color.chatTransparent));
        close.setVisibility(View.GONE);
        light.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
        editSetting.setVisibility(View.GONE);
        moreSetting.setVisibility(View.GONE);

    }

    /**
     * 当录像结束的时候显示按钮群
     */
    private void showBtnGroup() {
        footerView.setBackgroundColor(this.getResources().getColor(R.color.cameraSettingBtnBackColorNormal));
        close.setVisibility(View.VISIBLE);
        light.setVisibility(View.VISIBLE);
        change.setVisibility(View.VISIBLE);
        editSetting.setVisibility(View.VISIBLE);
        moreSetting.setVisibility(View.VISIBLE);
    }

}
