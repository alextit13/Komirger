package com.accherniakocich.android.findjob.classes;

import java.io.Serializable;

public class Message implements Serializable{

    private String name_user_from;
    private String name_user_to;
    private String text_message;
    private String date_message;
    private boolean readOrNot;
    private String uri_download_attach;

    public Message() {
    }

    public Message(String name_user_from, String name_user_to, String text_message, String date_message, boolean readOrNot, String uri_download_attach) {
        this.name_user_from = name_user_from;
        this.name_user_to = name_user_to;
        this.text_message = text_message;
        this.date_message = date_message;
        this.readOrNot = readOrNot;
        this.uri_download_attach = uri_download_attach;
    }

    public String getUri_download_attach() {
        return uri_download_attach;
    }

    public void setUri_download_attach(String uri_download_attach) {
        this.uri_download_attach = uri_download_attach;
    }

    public String getName_user_from() {
        return name_user_from;
    }

    public void setName_user_from(String name_user_from) {
        this.name_user_from = name_user_from;
    }

    public String getName_user_to() {
        return name_user_to;
    }

    public void setName_user_to(String name_user_to) {
        this.name_user_to = name_user_to;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getDate_message() {
        return date_message;
    }

    public void setDate_message(String date_message) {
        this.date_message = date_message;
    }

    @Override
    public String toString() {
        return "dateMessage = " + date_message;
    }

    public boolean isReadOrNot() {
        return readOrNot;
    }

    public void setReadOrNot(boolean readOrNot) {
        this.readOrNot = readOrNot;
    }
}
