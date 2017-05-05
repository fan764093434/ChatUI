package com.fsw.chat_ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

import com.fsw.chat_ui.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fsw on 2017/4/1.
 * 相机的工具类
 */

public class CameraHelper {
    /**
     * 上下文
     */
    private Context context;
    /**
     * camera对象
     */
    private Camera camera;

    private SurfaceHolder holder;
    /**
     * camera设置的参数对象
     */
    private Camera.Parameters parameters;
    /**
     * 屏幕高宽
     */
    private DisplayMetrics displayMetrics;
    /**
     * 当前对象单利
     */
    private static CameraHelper singleton;
    /**
     * 0代表前置摄像头，1代表后置摄像头
     */
    private int cameraPosition = 1;
    /**
     * 当前闪光灯模式的下标
     */
    private int lightMode = 0;
    /**
     * 用于存放几种闪光灯模式
     */
    private List<String> lightModes = new ArrayList<>();
    /**
     * 表示滑动这么远距离完成一次缩放
     */
    public static final int multiple = 10;

    /**
     * 录制视频类
     */
    private MediaRecorder mediaRecorder;

    /**
     * 此表示用来判断是否开始录制
     */
    private boolean isRecord;

    private CameraHelper() {
        lightModes.add(Camera.Parameters.FLASH_MODE_AUTO);
        lightModes.add(Camera.Parameters.FLASH_MODE_TORCH);
        lightModes.add(Camera.Parameters.FLASH_MODE_OFF);
    }

    /**
     * 获取当前对象的单利
     *
     * @return
     */
    public static CameraHelper getInstance() {
        if (singleton == null) {
            singleton = new CameraHelper();
        }
        return singleton;
    }

    /**
     * 设置上下文
     *
     * @param context
     * @return
     */
    public CameraHelper with(Context context) {
        this.context = context;
        displayMetrics = DisplayUtil.getDisplayMetrics(context);
        return getInstance();
    }

    /**
     * 获取与手机最吻合的预览比例
     *
     * @return
     */
    public Point getBestCameraResolution(List<Camera.Size> supportedPreviewSizes) {
        float tmp = 0f;
        float minDiff = 100f;
        float x_d_y = (float) displayMetrics.widthPixels / (float) displayMetrics.heightPixels;
        Camera.Size best = null;
        for (Camera.Size s : supportedPreviewSizes) {
            tmp = Math.abs(((float) s.height / (float) s.width) - x_d_y);
            if (tmp < minDiff) {
                minDiff = tmp;
                best = s;
            }
        }
        return new Point(best.width, best.height);
    }


    /**
     * 获取最大的级别
     *
     * @return
     */
    public int getMaxZoom() {
        return parameters.getMaxZoom();
    }

    /**
     * 设置holder
     *
     * @param holder
     */
    public void setHolder(SurfaceHolder holder) {
        //表明该Surface不包含原生数据，Surface用到的数据由其他对象提供，在Camera图像预览中就使用该类型的Surface，
        //有Camera负责提供给预览Surface数据，这样图像预览会比较流畅。
        this.holder = holder;
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setFixedSize(displayMetrics.widthPixels, displayMetrics.heightPixels); // 设置分辨率
        holder.addCallback(new SurfaceCallback());
    }

    /**
     * 获取camera对象
     *
     * @return
     */
    public CameraHelper getCamera() {
        camera = Camera.open();
        //设置camera视角旋转的角度
        camera.setDisplayOrientation(90);
        //获取相机参数
        parameters = camera.getParameters();
        //获取手机摄像头支持帧数范围。
        List<int[]> list = parameters.getSupportedPreviewFpsRange();
        //每秒相机帧数的范围
        parameters.setPreviewFpsRange(list.get(0)[0], list.get(0)[1]);
        //获取手机支持的照片尺寸
        List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();
        //设置拍照后的图片的分辨率
        if (sizeList.size() != 0) {
            parameters.setPictureSize(sizeList.get(sizeList.size() - 1).width, sizeList.get(sizeList.size() - 1).height);
        } else {
            parameters.setPictureSize(displayMetrics.widthPixels * 4, displayMetrics.heightPixels * 4);
        }
        Point point = getBestCameraResolution(parameters.getSupportedPreviewSizes());
        parameters.setPreviewSize(point.x, point.y);
        parameters.setVideoStabilization(true);
        //设置照片的输出格式
        parameters.setPictureFormat(PixelFormat.JPEG);
        //照片质量
        parameters.set("jpeg-quality", 100);
        //闪光灯模式
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        return getInstance();
    }

    /**
     * 切换前后摄像头
     *
     * @return
     * @throws IOException
     */
    public Camera changeCamera() throws IOException {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    //设置camera视角旋转的角度
                    camera.setDisplayOrientation(90);
                    camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    camera.setParameters(parameters);
                    camera.startPreview();//开始预览
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    //设置camera视角旋转的角度
                    camera.setDisplayOrientation(90);
                    camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    camera.setParameters(parameters);
                    camera.startPreview();//开始预览
                    cameraPosition = 1;
                    break;
                }
            }
        }
        return camera;
    }

    /**
     * 设置闪光灯模式
     * 并且更换图片指示
     *
     * @param light
     */
    public void setLightMode(ImageView light) {
        if (camera == null || parameters == null) {
            return;
        }
        lightMode++;
        if (lightMode >= lightModes.size()) {
            lightMode = 0;
        }
        String MODE = lightModes.get(lightMode);
        parameters.setFlashMode(MODE);
        if (MODE == Camera.Parameters.FLASH_MODE_AUTO) {
            light.setImageDrawable(context.getResources().getDrawable(R.drawable.chat_icon_flash_lamp_auto));
        } else if (MODE == Camera.Parameters.FLASH_MODE_TORCH) {
            light.setImageDrawable(context.getResources().getDrawable(R.drawable.chat_icon_flash_lamp_open));
        } else {
            light.setImageDrawable(context.getResources().getDrawable(R.drawable.chat_icon_flash_lamp_close));
        }
        camera.setParameters(parameters);
    }


    /**
     * 设置缩放
     *
     * @param zoom
     */
    public void zoom(int zoom) {
        if (camera == null || parameters == null) {
            return;
        }
        if (parameters.isSmoothZoomSupported()) {
            parameters.setZoom(zoom / multiple);
        }
        camera.setParameters(parameters);
    }

    /**
     * 获取当前闪光灯模式
     *
     * @return
     */
    public String getFlashMode() {
        return parameters.getFlashMode();
    }

    /**
     * 拍摄图片
     *
     * @param listener
     */
    public void takePhoto(final OnPictureSaveFinishListener listener) {
        final File file = new File(context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath(), getFileName() + ".jpg");
        // 控制摄像头自动对焦后才拍摄
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(context, "SD卡不可用，无法拍照", Toast.LENGTH_SHORT).show();
            return;
        }
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // 根据拍照所得的数据创建位图
                Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                // 创建一个位于SD卡上的文件
                try {
                    FileOutputStream fileOutStream = new FileOutputStream(file);
                    //把位图输出到指定的文件中
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream);
                    fileOutStream.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
                bm.recycle();
                bm = null;
                if (cameraPosition == 1) {
                    setPictureDegreeZero(file.getAbsolutePath(), ExifInterface.ORIENTATION_ROTATE_90);
                } else if (cameraPosition == 0) {
                    setPictureDegreeZero(file.getAbsolutePath(), ExifInterface.ORIENTATION_ROTATE_270);
                }
                listener.onFinish(file.getAbsolutePath());
            }
        });
    }


    /**
     * 将图片的旋转角度置为0
     *
     * @param path
     * @return void
     * @Title: setPictureDegreeZero
     * @date 2012-12-10 上午10:54:46
     */
    public static void setPictureDegreeZero(String path, int degree) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "" + degree);
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间格式化后作为拍照图片
     *
     * @return
     */
    private String getFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    /**
     * 触摸聚焦
     *
     * @param cameraView
     * @param event
     */
    public void focusOnTouch(SurfaceView cameraView, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = new Rect(x - 100, y - 100, x + 100, y + 100);
        int left = rect.left * 2000 / cameraView.getWidth() - 1000;
        int top = rect.top * 2000 / cameraView.getHeight() - 1000;
        int right = rect.right * 2000 / cameraView.getWidth() - 1000;
        int bottom = rect.bottom * 2000 / cameraView.getHeight() - 1000;
        // 如果超出了(-1000,1000)到(1000, 1000)的范围，则会导致相机崩溃
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        focusOnRect(new Rect(left, top, right, bottom));
    }

    /**
     * 开始聚焦
     *
     * @param rect
     */
    protected void focusOnRect(Rect rect) {
        if (camera != null) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); // 设置聚焦模式
            if (parameters.getMaxNumFocusAreas() > 0) {
                List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
                focusAreas.add(new Camera.Area(rect, 1000));
                parameters.setFocusAreas(focusAreas);
            }
            camera.cancelAutoFocus(); // 先要取消掉进程中所有的聚焦功能
            camera.setParameters(parameters); // 一定要记得把相应参数设置给相机
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                }
            });
        }
    }

    /**
     * 当图片拍摄成功后的监听
     */
    public interface OnPictureSaveFinishListener {
        void onFinish(String path);
    }

    /**
     * 视图生命周期的监听
     */
    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (camera == null) {//如果没有创建camera对象，则不能进行自动对焦操作
                getCamera();
            }
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.startPreview();
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        camera.setParameters(parameters);//实现相机的参数初始化
                        camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
                    }
                }
            });
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.stopPreview();
                // 记得释放
                camera.release();
                camera = null;
            }
        }
    }


    /**
     * 开始录制/停止录制
     */
    public void recordVideo() {
        if (isRecord) {
            isRecord = false;
            stopRecord();
        } else {
            isRecord = true;
            File file = new File(context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath() + "/" + getFileName() + ".mp4");
            // 创建mediaRecorder对象
            mediaRecorder = new MediaRecorder();
            //解锁相机以允许其他进程访问相机。
            camera.unlock();
            mediaRecorder.setCamera(camera);
            // 这两项需要放在setOutputFormat之前
            // 设置录制视频源为Camera(相机)
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // 从麦克风源进行录音
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //这两项需要放在setOutputFormat之后
            // 设置录制的视频编码h263 h264
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            //设置音频输出的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            // 设置视频文件输出的路径
            mediaRecorder.setOutputFile(file.getPath());
            mediaRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
            //视频旋转90度
            if (cameraPosition == 1) {
                mediaRecorder.setOrientationHint(90);
            } else if (cameraPosition == 0) {
                mediaRecorder.setOrientationHint(270);
            }
            //设置记录会话的最大持续时间（毫秒）
            mediaRecorder.setMaxDuration(30 * 1000);
            // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
            Point point = getBestCameraResolution(parameters.getSupportedVideoSizes());
            mediaRecorder.setVideoSize(point.x, point.y);
            //设置音频和视频比特率,必须放在设置编码和格式的后面，否则报错
            mediaRecorder.setVideoFrameRate(30);
            //设置预览区域
            mediaRecorder.setPreviewDisplay(holder.getSurface());
            try {
                // 准备录制
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        isRecord = false;
        if (mediaRecorder != null) {
            try {
                //下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
                //报错为：RuntimeException:stop failed
                mediaRecorder.setOnErrorListener(null);
                mediaRecorder.setOnInfoListener(null);
                mediaRecorder.setPreviewDisplay(null);
                mediaRecorder.stop();
                mediaRecorder.reset();
                // 释放资源
                mediaRecorder.release();
                mediaRecorder = null;
                camera.lock();
            } catch (IllegalStateException e) {
            } catch (RuntimeException e) {
            } catch (Exception e) {
            }
        }
    }

}