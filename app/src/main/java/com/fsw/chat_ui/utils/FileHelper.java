package com.fsw.chat_ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2017/4/5.
 */

public class FileHelper {

    /**
     * 根据路径删除文件
     */
    public static boolean deleteFileByPath(String path) {
        File file = new File(path);
        return file.delete();
    }

    /**
     * 根据图片路径计算图片的高宽
     *
     * @param path
     * @return
     */
    public static Point getWhByImagePath(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int w = options.outWidth;
        int h = options.outHeight;
        return new Point(w, h);
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 根据视频的路径获取视频的时长、高宽
     *
     * @param mUri
     * @return
     */
    public static Map<Integer, Integer> getVideoMessage(String mUri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        int duration = 0;//时长(毫秒)
        int width = 0;//宽
        int height = 0;//高
        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            }
            duration = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            width = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            height = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        } catch (Exception ex) {

        } finally {
            mmr.release();
        }
        Map<Integer, Integer> values = new HashMap<>();
        values.put(MediaMetadataRetriever.METADATA_KEY_DURATION, duration);
        values.put(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH, width);
        values.put(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT, height);
        return values;
    }

    /**
     * 获取视频中的一帧作为视频播放器的桌面画
     *
     * @param url
     * @return
     */
    public static Bitmap getVideoFirstFrame(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.FULL_SCREEN_KIND;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        Map<Integer, Integer> values = getVideoMessage(url);
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    values.get(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH),
                    values.get(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT),
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


}
