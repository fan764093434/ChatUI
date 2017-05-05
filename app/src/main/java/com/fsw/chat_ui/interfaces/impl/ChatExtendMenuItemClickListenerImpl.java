package com.fsw.chat_ui.interfaces.impl;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fsw.chat_ui.interfaces.ChatExtendMenuItemClickListener;
import com.fsw.chat_ui.ui.ChatPrepareRedPacketActivity;
import com.fsw.chat_ui.ui.ChatTransferAccountsActivity;
import com.fsw.chat_ui.ui.ChatVoiceCallActivity;

/**
 * Created by fsw on 2017/4/11.
 * handle the click event for extend menu
 */

public class ChatExtendMenuItemClickListenerImpl implements ChatExtendMenuItemClickListener {
    private Activity activity;

    public ChatExtendMenuItemClickListenerImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(int itemId, View view) {
        switch (itemId) {
            case 0:

                break;
            case 1://语音电话
                Intent intent = new Intent(activity, ChatVoiceCallActivity.class);
                activity.startActivity(intent);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5://发红包
                intent = new Intent(activity, ChatPrepareRedPacketActivity.class);
                activity.startActivity(intent);
                break;
            case 6://转账
                intent = new Intent(activity, ChatTransferAccountsActivity.class);
                activity.startActivity(intent);
                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12://戳一戳

                break;
            case 13:

                break;
            case 14:

                break;
        }
    }
}
