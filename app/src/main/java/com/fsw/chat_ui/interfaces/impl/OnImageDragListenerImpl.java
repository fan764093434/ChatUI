package com.fsw.chat_ui.interfaces.impl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.interfaces.OnImageDragListener;
import com.fsw.chat_ui.utils.DisplayUtil;

/**
 * Created by Admin on 2017/4/11.
 */

public class OnImageDragListenerImpl implements OnImageDragListener {

    private int left, width, top, height;
    private RelativeLayout.LayoutParams params;
    ImageView dragTV;
    private int notificationBarHeight;
    private DisplayMetrics displayMetrics;

    public OnImageDragListenerImpl(Activity context, ImageView dragTV) {
        this.dragTV = dragTV;
        //屏幕的高度
        displayMetrics = DisplayUtil.getDisplayMetrics(context);
        //通知栏高度
        notificationBarHeight = DisplayUtil.getNotificationBarHeight(context);
    }

    @Override
    public void onDragStartListener(ImageView imageView, int w, int y) {
        width = w;
        height = y;
        //获取View在屏幕中的位置
        int[] location = new int[2];
        imageView.getLocationInWindow(location);
        left = location[0];
        top = location[1];
        params = new RelativeLayout.LayoutParams(width, height);
        //此方法必须在getDrawingCache前调用，否则getDrawingCache将获取不到bitmap
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        dragTV.setImageBitmap(bitmap);
        params.setMargins(left, top - notificationBarHeight, displayMetrics.widthPixels - (left + width), displayMetrics.heightPixels - (top + height));
        dragTV.setLayoutParams(params);
        dragTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onImageDragStartListener(ImageView imageView, int ver) {
        params.setMargins(left, top - ver - notificationBarHeight, displayMetrics.widthPixels - (left + width), displayMetrics.heightPixels - (top + height) + ver);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        if (ver >= height) {
            //画提示文字在图片上
            dragTV.setImageBitmap(drawText(bitmap));
        } else {
            dragTV.setImageBitmap(bitmap);
        }
        dragTV.setLayoutParams(params);
    }

    @Override
    public void onImageDragStopListener() {
        dragTV.setVisibility(View.GONE);
    }

    @Override
    public void onCheckedListener(String path, boolean isChecked) {

    }

    //画文字
    private Bitmap drawText(Bitmap var0) {
        //创建一个新的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(var0);
        //获取画布
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(R.color.blue);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(0xffff0000);
        p.setTextSize(28);
        canvas.drawBitmap(bitmap, 0, 0, p);
        canvas.drawText("松开发送", width / 2 - 56, 28, p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }

}
