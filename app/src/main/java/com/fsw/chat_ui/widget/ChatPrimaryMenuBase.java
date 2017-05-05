package com.fsw.chat_ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatPrimaryMenuBase extends LinearLayout {

    protected Activity activity;

    protected InputMethodManager inputManager;

    private ChatPrimaryMenuListener chatPrimaryMenuListener;

    public void setChatPrimaryMenuListener(ChatPrimaryMenuListener chatPrimaryMenuListener) {
        this.chatPrimaryMenuListener = chatPrimaryMenuListener;
    }

    public ChatPrimaryMenuBase(Context context) {
        this(context, null);
    }

    public ChatPrimaryMenuBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatPrimaryMenuBase(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatPrimaryMenuBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        activity = (Activity) context;
        inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public interface ChatPrimaryMenuListener {

    }

    /**
     * hide keyboard
     */
    public void hideKeyboard() {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
