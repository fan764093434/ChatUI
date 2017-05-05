package com.fsw.chat_ui;

/**
 * Created by fsw on 2017/3/27.
 */

public class ChatMessage {
    /**
     * 发消息人的账号，昵称，头像
     * Send a person's account, nickname, Avatar
     */
    private String userInfo;
    private String username;
    private String userAvatar;
    /**
     * 收消息人的账号，昵称，头像
     * The recipient's account number, nickname, Avatar
     */
    private String info;
    private String name;
    private String avatar;

    /**
     * 消息的方向
     * Direction of message
     */
    public enum Direct {
        SEND,
        RECEIVE;

        private Direct() {
        }
    }

    /**
     * 消息的类型
     * Message type
     */
    public enum MessageType {
        TXT,//文本
        IMAGE,//图片
        FILE,//文件
        VIDEO,//视频
        VOICE,//语音
        LOCATION,//位置
        RED_ENVELOPE,//红包
        RED_ENVELOPE_STATUS,//红包状态
        SHAKING,//抖一抖
        CMD;//透传消息

        private MessageType() {
        }
    }

    /**
     * 当前聊天的类型
     * Current chat type
     */
    public enum ChatType {
        SINGLE_CHAT,//单聊
        GROUP_CHAT;//群聊

        private ChatType() {
        }
    }

}
