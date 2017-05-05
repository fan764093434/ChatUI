package com.fsw.chat_ui.entity;

import com.fsw.chat_ui.interfaces.ChatExtendMenuItemClickListener;

import java.io.Serializable;

/**
 * Created by Admin on 2017/3/25.
 */

public class ExtraMenu implements Serializable {

    public int id;

    public String name;

    public int background;

    public int icon;

    public ChatExtendMenuItemClickListener clickListener;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ChatExtendMenuItemClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ChatExtendMenuItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
