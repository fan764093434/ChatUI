package com.fsw.chat_ui.entity;

import java.io.Serializable;

/**
 * Created by Admin on 2017/3/24.
 */

public class Emotion implements Serializable {

    private String code = null;

    private String name = null;

    public Emotion() {
    }

    public Emotion(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
