package com.example.eventbus;

public class MessageEvent {
    // 成员变量根据自己的需求创建
    private int type;

    // 通过构造方法传递数据
    public MessageEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}