package com.fsw.chat_ui.entity;

import com.fsw.chat_ui.ChatMessage;

import java.io.Serializable;

/**
 * Created by Admin on 2017/3/27.
 */

public class Message implements Serializable {
    /**
     * 消息的方向
     */
    private ChatMessage.Direct direct;
    /**
     * 消息的类型
     */
    private ChatMessage.MessageType messageType;

    public ChatMessage.MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ChatMessage.MessageType messageType) {
        this.messageType = messageType;
    }

    public ChatMessage.Direct getDirect() {
        return direct;
    }

    public void setDirect(ChatMessage.Direct direct) {
        this.direct = direct;
    }
}
