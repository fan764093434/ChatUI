package com.fsw.chat_ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.MessageAdapter;
import com.fsw.chat_ui.entity.Message;

/**
 * Created by fsw on 2017/3/7.
 * use 消息列表
 */

public class ChatMessageListView extends RelativeLayout {

    /**
     * 消息列表
     * 适配器
     */
    protected ListView listView;
    private MessageAdapter adapter;
    /**
     * 下拉刷新控件
     */
    protected SwipeRefreshLayout swipeRefreshLayout;

    public ChatMessageListView(Context context) {
        this(context, null);
    }

    public ChatMessageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatMessageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_view_message_list, this);
        listView = (ListView) findViewById(R.id.list);
        adapter = new MessageAdapter(context);
        listView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
        swipeRefreshLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public interface MessageListItemClickListener {
        /**
         * there is default handling when bubble is clicked, if you want handle it, return true
         * another way is you implement in onBubbleClick() of chat row
         *
         * @param message
         * @return
         */
        boolean onBubbleClick(Message message);

    }

    /**
     * set click listener
     *
     * @param listener
     */
    public void setItemClickListener(MessageListItemClickListener listener) {
        if (adapter != null) {
            adapter.setItemClickListener(listener);
        }
    }

}
