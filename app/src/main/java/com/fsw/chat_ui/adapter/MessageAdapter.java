package com.fsw.chat_ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fsw.chat_ui.ChatMessage;
import com.fsw.chat_ui.entity.Message;
import com.fsw.chat_ui.widget.ChatMessageListView;
import com.fsw.chat_ui.widget.chatrow.ChatRow;
import com.fsw.chat_ui.widget.chatrow.ChatRowFile;
import com.fsw.chat_ui.widget.chatrow.ChatRowImage;
import com.fsw.chat_ui.widget.chatrow.ChatRowLocation;
import com.fsw.chat_ui.widget.chatrow.ChatRowRedPacket;
import com.fsw.chat_ui.widget.chatrow.ChatRowRedPacketStatus;
import com.fsw.chat_ui.widget.chatrow.ChatRowShaking;
import com.fsw.chat_ui.widget.chatrow.ChatRowText;
import com.fsw.chat_ui.widget.chatrow.ChatRowVideo;
import com.fsw.chat_ui.widget.chatrow.ChatRowVoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/3/27.
 */

public class MessageAdapter extends BaseAdapter {

    private final int MESSAGE_TYPE_RECEIVE_TXT = 1;
    private final int MESSAGE_TYPE_SEND_TXT = 2;
    private final int MESSAGE_TYPE_RECEIVE_IMAGE = 3;
    private final int MESSAGE_TYPE_SEND_IMAGE = 4;
    private final int MESSAGE_TYPE_RECEIVE_FILE = 5;
    private final int MESSAGE_TYPE_SEND_FILE = 6;
    private final int MESSAGE_TYPE_RECEIVE_VIDEO = 7;
    private final int MESSAGE_TYPE_SEND_VIDEO = 8;
    private final int MESSAGE_TYPE_RECEIVE_VOICE = 9;
    private final int MESSAGE_TYPE_SEND_VOICE = 10;
    private final int MESSAGE_TYPE_RECEIVE_LOCATION = 11;
    private final int MESSAGE_TYPE_SEND_LOCATION = 12;
    private final int MESSAGE_TYPE_RECEIVE_RED_ENVELOPE = 13;
    private final int MESSAGE_TYPE_SEND_RED_ENVELOPE = 14;
    private final int MESSAGE_TYPE_RECEIVE_RED_ENVELOPE_STATUS = 15;
    private final int MESSAGE_TYPE_SEND_RED_ENVELOPE_STATUS = 16;
    private final int MESSAGE_TYPE_RECEIVE_CMD = 17;
    private final int MESSAGE_TYPE_SEND_SHAKING = 18;
    private final int MESSAGE_TYPE_RECEIVE_SHAKING = 19;
    private final int MESSAGE_TYPE_SEND_CMD = 20;

    private Context context;

    private List<Message> list;

    private ChatMessageListView.MessageListItemClickListener itemClickListener;

    public ChatMessageListView.MessageListItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ChatMessageListView.MessageListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MessageAdapter(Context context) {
        this.context = context;
        list = new ArrayList();
        Message message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.TXT);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.TXT);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.IMAGE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.IMAGE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.RED_ENVELOPE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.RED_ENVELOPE_STATUS);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.RED_ENVELOPE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.RED_ENVELOPE_STATUS);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.VOICE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.VOICE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.VIDEO);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.VIDEO);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.FILE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.FILE);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.LOCATION);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.LOCATION);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.RECEIVE);
        message.setMessageType(ChatMessage.MessageType.SHAKING);
        list.add(message);
        message = new Message();
        message.setDirect(ChatMessage.Direct.SEND);
        message.setMessageType(ChatMessage.MessageType.SHAKING);
        list.add(message);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Message getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // 每个convert view都会调用此方法，获得当前所需要的view样式
    @Override
    public int getItemViewType(int position) {
        Message message = list.get(position);
        ChatMessage.MessageType messageType = message.getMessageType();
        switch (messageType) {
            case TXT:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_TXT : MESSAGE_TYPE_SEND_TXT;
            case IMAGE:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_IMAGE : MESSAGE_TYPE_SEND_IMAGE;
            case RED_ENVELOPE:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_RED_ENVELOPE : MESSAGE_TYPE_SEND_RED_ENVELOPE;
            case VOICE:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_VOICE : MESSAGE_TYPE_SEND_VOICE;
            case VIDEO:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_VIDEO : MESSAGE_TYPE_SEND_VIDEO;
            case FILE:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_FILE : MESSAGE_TYPE_SEND_FILE;
            case RED_ENVELOPE_STATUS:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_RED_ENVELOPE_STATUS : MESSAGE_TYPE_SEND_RED_ENVELOPE_STATUS;
            case LOCATION:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_LOCATION : MESSAGE_TYPE_SEND_LOCATION;
            case SHAKING:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_SHAKING : MESSAGE_TYPE_SEND_SHAKING;
            case CMD:
                return message.getDirect() == ChatMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECEIVE_CMD : MESSAGE_TYPE_SEND_CMD;
            default:
                return -1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return ChatMessage.MessageType.values().length * 2 + 1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = list.get(position);
        if (message == null || message.getMessageType() == null) {
            throw new RuntimeException("Message type not specified");
        }
        if (convertView == null) {
            convertView = createChatRow(message);
        }
        ((ChatRow) convertView).setUpView(message, itemClickListener);
        return convertView;
    }


    private ChatRow createChatRow(Message message) {
        if (message == null || message.getMessageType() == null) {
            throw new RuntimeException("Message type not specified");
        }
        ChatRow chatRow = null;
        switch (message.getMessageType()) {
            case TXT:
                chatRow = new ChatRowText(context, message);
                break;
            case IMAGE:
                chatRow = new ChatRowImage(context, message);
                break;
            case RED_ENVELOPE:
                chatRow = new ChatRowRedPacket(context, message);
                break;
            case VOICE:
                chatRow = new ChatRowVoice(context, message);
                break;
            case VIDEO:
                chatRow = new ChatRowVideo(context, message);
                break;
            case FILE:
                chatRow = new ChatRowFile(context, message);
                break;
            case RED_ENVELOPE_STATUS:
                chatRow = new ChatRowRedPacketStatus(context, message);
                break;
            case LOCATION:
                chatRow = new ChatRowLocation(context, message);
                break;
            case SHAKING:
                chatRow = new ChatRowShaking(context, message);
                break;
            case CMD:
                break;
        }
        return chatRow;
    }
}
