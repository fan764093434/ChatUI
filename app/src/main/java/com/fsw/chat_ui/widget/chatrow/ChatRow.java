package com.fsw.chat_ui.widget.chatrow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.fsw.chat_ui.entity.Message;
import com.fsw.chat_ui.widget.ChatMessageListView;

/**
 * Created by Admin on 2017/3/27.
 */

public abstract class ChatRow extends LinearLayout {

    protected Context context;

    protected Activity activity;

    protected LayoutInflater inflater;

    protected Message message;

    protected ChatMessageListView.MessageListItemClickListener itemClickListener;

    public ChatRow(Context context, Message message) {
        super(context);
        this.context = context;
        activity = (Activity) context;
        this.message = message;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        inflater = LayoutInflater.from(context);
        onInflateView();
        onViewFindById();
        onSetUpView();
    }

    public void setUpView(Message message, ChatMessageListView.MessageListItemClickListener itemClickListener) {
        this.message = message;
        this.itemClickListener = itemClickListener;
        onSetClickListener();
    }

    /**
     * 加载布局
     */
    protected abstract void onInflateView();

    /**
     * 查找元素
     */
    protected abstract void onViewFindById();

    /**
     * 设置元素
     */
    protected abstract void onSetUpView();

    /**
     * 元素设置点击事件
     */
    protected abstract void onSetClickListener();


}
