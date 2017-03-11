package com.example.pattimura.wims.Model;

import android.text.format.DateFormat;

/**
 * Created by desmoncode on 11/03/17.
 */

public class ChatGrup {
    private String nama,message,id,formattedTime;
    private long waktu;

    public ChatGrup(String nama, String message, String id, long waktu) {
        this.nama = nama;
        this.message = message;
        this.id = id;
        this.waktu = waktu;
    }

    public ChatGrup(){

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
