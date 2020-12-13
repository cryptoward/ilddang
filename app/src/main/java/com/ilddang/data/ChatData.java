package com.ilddang.data;

public class ChatData {
    public int chatType;
    public String content;
    public String time;

    public ChatData(int chatType, String content, String time) {
        this.chatType = chatType;
        this.content = content;
        this.time = time;
    }

    public int getViewType() {
        return chatType;
    }
}
