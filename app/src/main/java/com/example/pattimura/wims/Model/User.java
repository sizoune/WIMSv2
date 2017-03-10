package com.example.pattimura.wims.Model;

/**
 * Created by desmoncode on 09/03/17.
 */

public class User {
    private String id, nama, email, password, notel, sd, smp, sma, kuliah, kerja, urlgambar;

    public User(String id, String nama, String email, String password, String notel, String sd, String smp, String sma, String kuliah, String kerja, String urlgambar) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.notel = notel;
        this.sd = sd;
        this.smp = smp;
        this.sma = sma;
        this.kuliah = kuliah;
        this.kerja = kerja;
        this.urlgambar = urlgambar;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotel() {
        return notel;
    }

    public void setNotel(String notel) {
        this.notel = notel;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getSmp() {
        return smp;
    }

    public void setSmp(String smp) {
        this.smp = smp;
    }

    public String getSma() {
        return sma;
    }

    public void setSma(String sma) {
        this.sma = sma;
    }

    public String getKuliah() {
        return kuliah;
    }

    public void setKuliah(String kuliah) {
        this.kuliah = kuliah;
    }

    public String getKerja() {
        return kerja;
    }

    public void setKerja(String kerja) {
        this.kerja = kerja;
    }

    public String getUrlgambar() {
        return urlgambar;
    }

    public void setUrlgambar(String urlgambar) {
        this.urlgambar = urlgambar;
    }
}
