package com.example.pattimura.wims.Model;

import android.text.format.DateFormat;

/**
 * Created by desmoncode on 03/03/17.
 */

public class ListChat {
    private String displayName;
    private String status;
    private String avatar;
    private String user;
    /*private String message;
    private long time;
    private String formattedTime;*/

    /*public ListChat(String displayName, String status, String avatar, String message, long time) {
        this.displayName = displayName;
        this.status = status;
        this.avatar = avatar;
        this.message = message;
        this.time = time;

    }

    public ListChat(String displayName, String status, String avatar, String message, long time, String formattedTime) {
        this.displayName = displayName;
        this.status = status;
        this.avatar = avatar;
        this.message = message;
        this.time = time;
        this.formattedTime = formattedTime;
    }*/

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ListChat(String displayName, String status, String avatar) {
        this.displayName = displayName;
        this.status = status;
        this.avatar = avatar;
    }

    public ListChat() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /*public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime2(long time) {
        this.time = time;

        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - time;

        if(timeDifference < oneDayInMillis){
            formattedTime = DateFormat.format("hh:mm a", time).toString();
        }else{
            formattedTime = DateFormat.format("dd MMM - hh:mm a", time).toString();
        }
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getFormattedTime(){
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - time;

        if(timeDifference < oneDayInMillis){
            return DateFormat.format("hh:mm a", time).toString();
        }else{
            return DateFormat.format("dd MMM - hh:mm a", time).toString();
        }
    }*/
}
