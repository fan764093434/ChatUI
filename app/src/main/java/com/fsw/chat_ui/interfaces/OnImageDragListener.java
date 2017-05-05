package com.fsw.chat_ui.interfaces;

import android.widget.ImageView;

/**
 * Created by Admin on 2017/4/6.
 */

public interface OnImageDragListener {

    void onDragStartListener(ImageView imageView, int w, int y);

    void onImageDragStartListener(ImageView imageView, int ver);

    void onImageDragStopListener();

    void onCheckedListener(String path, boolean isChecked);
}
