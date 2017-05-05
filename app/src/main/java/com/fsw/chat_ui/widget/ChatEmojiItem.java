package com.fsw.chat_ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.adapter.EmojiAdapter;
import com.fsw.chat_ui.entity.Emotion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatEmojiItem extends LinearLayout {

    private GridView emojiGridView;
    private EmojiAdapter emojiAdapter;
    private List<Emotion> emotions;

    private OnEmojiGridItemClickListener onEmojiGridItemClickListener;

    public void setOnEmojiGridItemClickListener(OnEmojiGridItemClickListener onEmojiGridItemClickListener) {
        this.onEmojiGridItemClickListener = onEmojiGridItemClickListener;
    }

    public ChatEmojiItem(Context context, List<Emotion> emotions) {
        super(context);
        this.emotions = emotions;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_emoji_item, this);
        emojiGridView = (GridView) findViewById(R.id.emoji_grid_view);
        if (emotions == null) {
            emotions = new ArrayList<>();
        }
        emojiAdapter = new EmojiAdapter(context, emotions);
        emojiGridView.setAdapter(emojiAdapter);
        emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onEmojiGridItemClickListener != null) {
                    onEmojiGridItemClickListener.onEmojiGridItemClickListener(emotions.get(position));
                }
            }
        });
    }

    protected interface OnEmojiGridItemClickListener {
        /**
         * 当表情选择事件
         */
        void onEmojiGridItemClickListener(Emotion emotion);
    }

}
