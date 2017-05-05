package com.fsw.chat_ui.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.Emotion;
import com.fsw.chat_ui.entity.ExtraMenu;
import com.fsw.chat_ui.interfaces.OnImageDragListener;
import com.fsw.chat_ui.ui.ChatCameraActivity;
import com.fsw.chat_ui.ui.ChatPrepareRedPacketActivity;

import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatOperateMenu extends LinearLayout implements View.OnClickListener {
    /**
     * 额外拓展功能的最外层容器
     */
    private FrameLayout extendMenuContainer;
    /**
     * 打开表情选择按钮
     */
    private ImageView openEmoji;
    /**
     * 图片按钮图标
     */
    private ImageView image;
    /**
     * 发语音图标
     */
    private ImageView microphone;
    /**
     * 表情选择
     */
    private ChatEmojiMenu emojiContainer;

    /**
     * 打开更多功能的按钮
     */
    private ImageView openExtra;
    /**
     * 发红包按钮
     */
    private ImageView senPacket;
    /**
     * 发红包按钮
     */
    private ImageView takePhoto;
    /**
     * 更多功能列表
     */
    private ChatExtraMenu extraContainer;
    /**
     *
     */
    private ChatImageMenu imageContainer;


    protected OnOperateMenuClickListener onOperateMenuClickListener;

    public void setOnOperateMenuClickListener(OnOperateMenuClickListener onOperateMenuClickListener) {
        this.onOperateMenuClickListener = onOperateMenuClickListener;
    }

    public ChatOperateMenu(Context context) {
        this(context, null);
    }

    public ChatOperateMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatOperateMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatOperateMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_view_operate_menu, this);
        openEmoji = (ImageView) findViewById(R.id.open_emoji);
        openExtra = (ImageView) findViewById(R.id.open_extra);
        senPacket = (ImageView) findViewById(R.id.sen_packet);
        takePhoto = (ImageView) findViewById(R.id.take_photo);
        microphone = (ImageView) findViewById(R.id.microphone);
        image = (ImageView) findViewById(R.id.image);
        extendMenuContainer = (FrameLayout) findViewById(R.id.extend_menu_container);
        emojiContainer = (ChatEmojiMenu) findViewById(R.id.emoji_container);
        extraContainer = (ChatExtraMenu) findViewById(R.id.extra_container);
        imageContainer = (ChatImageMenu) findViewById(R.id.image_container);
        findViewById(R.id.open_emoji_view).setOnClickListener(this);
        findViewById(R.id.open_extra_view).setOnClickListener(this);
        findViewById(R.id.sen_packet_view).setOnClickListener(this);
        findViewById(R.id.take_photo_view).setOnClickListener(this);
        findViewById(R.id.image_view).setOnClickListener(this);
        findViewById(R.id.microphone_view).setOnClickListener(this);
        emojiContainer.setOnEmojiEmojiItemClickListener(new ChatEmojiMenu.OnEmojiEmojiItemClickListener() {
            @Override
            public void onEmojiEmojiItemClickListener(Emotion emotion) {
                if (onOperateMenuClickListener != null) {
                    onOperateMenuClickListener.onEmojiPressListener(emotion);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_emoji_view:
                resetBtnIcon();
                showEmoji();
                break;
            case R.id.open_extra_view:
                resetBtnIcon();
                showExtra();
                break;
            case R.id.sen_packet_view:
                resetBtnIcon();
                Intent intent = new Intent(getContext(), ChatPrepareRedPacketActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.take_photo_view:
                resetBtnIcon();
                intent = new Intent(getContext(), ChatCameraActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.image_view:
                resetBtnIcon();
                showImageMenu();
                break;
            case R.id.microphone_view:
                resetPage();
                if (onOperateMenuClickListener != null) {
                    onOperateMenuClickListener.onMicrophoneClickListener(microphone);
                }
                break;
        }
    }


    private void resetBtnIcon() {
        onOperateMenuClickListener.resetPage();
        openEmoji.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_emoticon_normal));
        openExtra.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_more_normal));
        image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_image_normal));
        microphone.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_microphone_normal));
    }

    /**
     * 重置页面的布局
     */
    public void resetPage() {
        onOperateMenuClickListener.resetPage();
        extendMenuContainer.setVisibility(View.GONE);
        emojiContainer.setVisibility(View.GONE);
        extraContainer.setVisibility(View.GONE);
        imageContainer.setVisibility(View.GONE);
        openEmoji.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_emoticon_normal));
        openExtra.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_more_normal));
        image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_image_normal));
        microphone.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_microphone_normal));
    }

    /**
     * 点击打开表情选择时的事件处理
     */
    private void showEmoji() {
        extraContainer.setVisibility(View.GONE);
        imageContainer.setVisibility(View.GONE);
        if (emojiContainer.getVisibility() == VISIBLE) {
            openEmoji.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_emoticon_normal));
            emojiContainer.setVisibility(View.GONE);
            extendMenuContainer.setVisibility(View.GONE);
        } else {
            openEmoji.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_emoticon_pressed));
            emojiContainer.setVisibility(View.VISIBLE);
            extendMenuContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击更多功能时的事件处理
     */
    private void showExtra() {
        emojiContainer.setVisibility(View.GONE);
        imageContainer.setVisibility(View.GONE);
        if (extraContainer.getVisibility() == VISIBLE) {
            openExtra.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_more_normal));
            extraContainer.setVisibility(View.GONE);
            extendMenuContainer.setVisibility(View.GONE);
        } else {
            openExtra.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_more_pressed));
            extraContainer.setVisibility(View.VISIBLE);
            extendMenuContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 展示图片容器
     */
    private void showImageMenu() {
        emojiContainer.setVisibility(View.GONE);
        extraContainer.setVisibility(View.GONE);
        if (imageContainer.getVisibility() == VISIBLE) {
            image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_image_normal));
            imageContainer.setVisibility(View.GONE);
            extendMenuContainer.setVisibility(View.GONE);
        } else {
            image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chat_icon_image_pressed));
            imageContainer.setVisibility(View.VISIBLE);
            extendMenuContainer.setVisibility(View.VISIBLE);
            //此时再来进行加载图片操作
            imageContainer.loadAllImage();
        }
    }


    public void registerMenuItem(List<ExtraMenu> menus) {
        extraContainer.add(menus);
    }

    /**
     * register menu item
     */
    public void setOnImageDragListener(OnImageDragListener listener) {
        imageContainer.setOnImageDragListener(listener);
    }


    public void setOnOpenAlbumListener(ChatImageMenu.OnOpenAlbumListener onOpenAlbumListener) {
        imageContainer.setOnOpenAlbumListener(onOpenAlbumListener);
    }

    protected interface OnOperateMenuClickListener {
        /**
         * 当表情选择事件
         *
         * @param emotion
         */
        void onEmojiPressListener(Emotion emotion);

        /**
         * 当点击发语音按钮
         */
        void onMicrophoneClickListener(ImageView view);

        /**
         * 重置页面
         */
        void resetPage();
    }

}
