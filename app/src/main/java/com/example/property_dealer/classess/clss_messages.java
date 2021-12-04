package com.example.property_dealer.classess;

public class clss_messages {

    private  String messagesID,messages,senderID,imageUrl;
    private long timestamp;


    public clss_messages(String messagesID, String messages, String senderID, String imageUrl, long timestamp) {
        this.messagesID = messagesID;
        this.messages = messages;
        this.senderID = senderID;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }


    public String getMessagesID() {
        return messagesID;
    }

    public void setMessagesID(String messagesID) {
        this.messagesID = messagesID;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
