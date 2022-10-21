package com.example.restaurtantchatbotapp.models;

public class Message {
    //detects if the message being sent is from the user or the bot to make the UI clearer
    private String message;
    private boolean isReceived;

    public Message(String message, boolean isReceived) {
        this.message = message;
        this.isReceived = isReceived;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsReceived() {
        return isReceived;
    }


}
