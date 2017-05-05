package com.fsw.chat_ui.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.fsw.chat_ui.R;

/**
 * Created by Admin on 2017/4/5.
 */

public class DialogHelper {
    /**
     * 图片处理提示
     */
    public static Dialog compressImageTip(Context context) {
        Dialog dialog = new Dialog(context, R.style.loading_dialog);
        dialog.setContentView(R.layout.chat_dialog_compress_image_tip);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        DisplayMetrics displayMetrics = DisplayUtil.getDisplayMetrics(context);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (displayMetrics.widthPixels * 0.7);
        dialog.getWindow().setAttributes(params);
        return dialog;
    }
}
