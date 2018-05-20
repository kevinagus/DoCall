package com.sons_of_interaction.docall;

import com.sons_of_interaction.docall.Doctor;

import java.util.Date;

public class Visit {

    private String fcPatient;
    private String symptoms;
    private String doctor;
    private String date;
    private String address;

    public Visit(){

    }

    public Visit(String fcPatient,String sintomi,String doctor,String date,String address) {
        this.symptoms=sintomi;
        this.doctor=doctor;
        this.date=date;
        this.fcPatient=fcPatient;
        this.address=address;
    }

    public String getFcPatient() {
        return fcPatient;
    }

    public void setFcPatient(String fcPatient) {
        this.fcPatient = fcPatient;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String sintomi) {
        this.symptoms = sintomi;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}