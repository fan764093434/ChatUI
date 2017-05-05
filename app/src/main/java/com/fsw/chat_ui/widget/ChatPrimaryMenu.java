package com.fsw.chat_ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.Emotion;
import com.fsw.chat_ui.entity.ExtraMenu;
import com.fsw.chat_ui.interfaces.OnImageDragListener;

import java.util.List;

/**
 * Created by Admin on 2017/3/25.
 */

public class ChatPrimaryMenu extends ChatPrimaryMenuBase {
    /**
     * 判断是否进行初始化操作
     */
    private boolean isInit = false;
    /**
     * 输入菜单
     */
    private ChatInputMenu chatInputMenu;
    /**
     * 更多菜单
     */
    private ChatOperateMenu chatOperateMenu;

    public ChatPrimaryMenu(Context context) {
        this(context, null);
    }

    public ChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatPrimaryMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ChatPrimaryMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.chat_view_primary_menu, this);
        chatInputMenu = (ChatInputMenu) findViewById(R.id.chat_input_menu);
        chatOperateMenu = (ChatOperateMenu) findViewById(R.id.chat_operate_menu);
        chatInputMenu.setOnOperateOpenButtonClickListener(new ChatInputMenu.OnOperateOpenButtonClickListener() {
            @Override
            public void onOperateOpenButtonClickListener(ImageView openOperateMenuBtn) {
                hideKeyboard();
                if (!isInit) {
                    throw new RuntimeException("ChatPrimaryMenu uninitialized，Please call registerExtendMenuItem method first!");
                }
                if (chatOperateMenu.getVisibility() == VISIBLE) {
                    chatOperateMenu.setVisibility(View.GONE);
                    chatOperateMenu.resetPage();
                    openOperateMenuBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.chat_input_menu_open_more_normal));
                } else {
                    chatOperateMenu.setVisibility(View.VISIBLE);
                    openOperateMenuBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.chat_input_menu_open_more_pressed));
                }
            }

            @Override
            public void onInputClickListener(ImageView openOperateMenuBtn) {
                chatOperateMenu.setVisibility(View.GONE);
                chatOperateMenu.resetPage();
                openOperateMenuBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.chat_input_menu_open_more_normal));
            }
        });
        //点击表情
        chatOperateMenu.setOnOperateMenuClickListener(new ChatOperateMenu.OnOperateMenuClickListener() {
            @Override
            public void onEmojiPressListener(Emotion emotion) {
                chatInputMenu.addTextToMessageView(emotion);
            }

            @Override
            public void onMicrophoneClickListener(ImageView view) {
                chatInputMenu.showSendVoiceView(view);
            }

            @Override
            public void resetPage() {
                chatInputMenu.resetPage();
            }

        });

    }

    /**
     * 添加扩展菜单
     */
    public void registerExtendMenuItem(List<ExtraMenu> menus, SiriWaveViewNine siriWaveViewNine) {
        chatOperateMenu.registerMenuItem(menus);
        chatInputMenu.registerSiriWavaView(siriWaveViewNine);
        isInit = true;
    }

    /**
     * 设置当图片拖动发送操作
     */
    public void setOnImageDragListener(OnImageDragListener listener) {
        chatOperateMenu.setOnImageDragListener(listener);
    }

    /**
     * 当点击打开相册按钮事件
     *
     * @param onOpenAlbumListener
     */
    public void setOnOpenAlbumListener(ChatImageMenu.OnOpenAlbumListener onOpenAlbumListener) {
        chatOperateMenu.setOnOpenAlbumListener(onOpenAlbumListener);
    }


}
