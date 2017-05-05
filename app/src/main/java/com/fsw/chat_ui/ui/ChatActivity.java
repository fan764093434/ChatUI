package com.fsw.chat_ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.R;
import com.fsw.chat_ui.entity.Message;
import com.fsw.chat_ui.interfaces.impl.ChatExtendMenuItemClickListenerImpl;
import com.fsw.chat_ui.interfaces.impl.OnImageDragListenerImpl;
import com.fsw.chat_ui.utils.ExtraMenuUtil;
import com.fsw.chat_ui.utils.GlideImageLoader;
import com.fsw.chat_ui.widget.ChatImageMenu;
import com.fsw.chat_ui.widget.ChatMessageListView;
import com.fsw.chat_ui.widget.ChatPrimaryMenu;
import com.fsw.chat_ui.widget.ProjectToolbar;
import com.fsw.chat_ui.widget.SiriWaveViewNine;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {
    /**
     * 主界面
     */
    @InjectView(R.id.main)
    View main;
    /**
     * 自定义导航栏
     */
    @InjectView(R.id.toolbar)
    ProjectToolbar toolbar;
    @InjectView(R.id.message_list)
    ChatMessageListView messageList;
    /**
     * 主操作菜单
     */
    @InjectView(R.id.primary_menu)
    ChatPrimaryMenu primaryMenu;
    /**
     * 待发送图片展示区域
     */
    @InjectView(R.id.dragTV)
    ImageView dragTV;
    /**
     * 发语音音波提示
     */
    @InjectView(R.id.siri_wave)
    SiriWaveViewNine siriWave;
    /**
     * 图片选择对象
     */
    private ImagePicker imagePicker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
//        //启动我的测试服务
//        startService(new Intent(this, MessageService.class));
//        startService(new Intent(this, MessageGuardService.class));
//        //启动jobService服务，判断当前版本是否大于5.0，否则会报错，导致程序崩溃
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startService(new Intent(this, WakeUpService.class));
//        }
        toolbar.setCenterTitle("樊述炜");
        toolbar.setMenuOneVisitable(true);
        toolbar.setSubtitle("手机wifi在线");
        toolbar.getNavigationIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setOperateMenu();
        setMessageList();
    }

    /**
     * 设置消息列表
     */
    private void setMessageList() {
        messageList.setItemClickListener(new ChatMessageListView.MessageListItemClickListener() {
            @Override
            public boolean onBubbleClick(Message message) {
                if (message.getMessageType() == ChatMessage.MessageType.SHAKING) {
                    Log.e("fsw--", "抖一抖");
                    Animation animation = AnimationUtils.loadAnimation(ChatActivity.this, R.anim.chat_shake_anim);
                    main.startAnimation(animation);
                }
                return false;
            }
        });
    }

    /**
     * 设置操作菜单
     */
    private void setOperateMenu() {
        //读取配置文件中拓展菜单
        InputStream inputStream = null;
        try {
            inputStream = this.getResources().getAssets().open("extra_menu_single_chat.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加扩展菜单
        primaryMenu.registerExtendMenuItem(ExtraMenuUtil.getExtraMenu(inputStream, new ChatExtendMenuItemClickListenerImpl(this)), siriWave);
        //设置当图片拖动发送操作
        primaryMenu.setOnImageDragListener(new OnImageDragListenerImpl(this, dragTV));
        //设置当点击打开相册操作
        primaryMenu.setOnOpenAlbumListener(new ChatImageMenu.OnOpenAlbumListener() {
            @Override
            public void onOpenAlbumListener() {
                imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());
                Intent intent = new Intent(ChatActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }


    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


}
