package com.example.pattimura.wims.Model;

/**
 * Created by wildan on 11/03/17.
 */

public class BagianUser {
    private String judul,isi;

    public BagianUser(String judul, String isi) {
        this.judul = judul;
        this.isi = isi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
