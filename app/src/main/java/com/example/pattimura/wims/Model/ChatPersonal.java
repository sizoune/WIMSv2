package com.example.pattimura.wims.Model;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by desmoncode on 04/03/17.
 */

public class ChatPersonal {
    private String message,id,formattedTime;
    private long waktu;

    public ChatPersonal(String message, String id, Long waktu, String formattedTime) {
        this.message = message;
        this.id = id;
        this.waktu = waktu;
        this.formattedTime=formattedTime;
    }

    public ChatPersonal(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getWaktu() {
        return waktu;
    }

    public void setWaktu(long waktu) {

        this.waktu = waktu;

        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - waktu;

        if(timeDifference < oneDayInMillis){
            formattedTime = DateFormat.format("kk:mm", waktu).toString();
        }else{
            formattedTime = DateFormat.format("dd MMM - kk:mm ", waktu).toString();
        }

    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getFormattedTime(){
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - waktu;

        if(timeDifference < oneDayInMillis){
            return DateFormat.format("kk:mm", waktu).toString();
        }else{
            return DateFormat.format("dd MMM - kk:mm", waktu).toString();
        }
    }
}
