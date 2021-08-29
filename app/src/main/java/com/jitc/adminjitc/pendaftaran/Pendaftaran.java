package com.jitc.adminjitc.pendaftaran;

public class Pendaftaran {
    String nama, jeniskelamin,email , nohp, asalkampus, course, image, date, time, key;

    public Pendaftaran(String nama, String jeniskelamin, String email, String nohp, String asalkampus, String course, String image, String date, String time, String key) {
        this.nama = nama;
        this.jeniskelamin = jeniskelamin;
        this.email = email;
        this.nohp = nohp;
        this.asalkampus = asalkampus;
        this.course = course;
        this.image = image;
        this.date = date;
        this.time = time;
        this.key = key;
    }

    public Pendaftaran(){

    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getAsalkampus() {
        return asalkampus;
    }

    public void setAsalkampus(String asalkampus) {
        this.asalkampus = asalkampus;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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
}
