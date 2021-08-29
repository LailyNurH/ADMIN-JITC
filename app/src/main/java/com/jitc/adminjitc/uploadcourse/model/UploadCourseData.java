package com.jitc.adminjitc.uploadcourse.model;

public class UploadCourseData {
    String title,durasi,harga,deskripsi, image, date, time, key;

    public UploadCourseData(){
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public UploadCourseData(String title, String durasi, String harga, String deskripsi, String image, String date, String time, String key) {
        this.title = title;
        this.durasi = durasi;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.image = image;
        this.date = date;
        this.time = time;
        this.key = key;
    }
}
