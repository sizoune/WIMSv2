package com.example.pattimura.wims.Model;

/**
 * Created by desmoncode on 03/03/17.
 */

public class ListChat {
    private String displayName;
    private String status;
    private String avatar;
    private String message;
    private long time;

    public ListChat(String displayName, String status, String avatar, String message, long time) {
        this.displayName = displayName;
        this.status = status;
        this.avatar = avatar;
        this.message = message;
        this.time = time;
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

    public long getTime() {
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
}
