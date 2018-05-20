package com.sons_of_interaction.docall;

/**
 * Created by User on 21/02/2018.
 */

public class Patient {

    private String name;
    private String surname;
    private String birthDate;
    private String password;
    private String fiscalCode;
    private boolean prenotazioneConfermata;

    public Patient(String name,String surname,String birthDate,String password,String fiscalCode,boolean prenotazioneConfermata) {
        this.name=name;
        this.surname=surname;
        this.birthDate=birthDate;
        this.password=password;
        this.fiscalCode=fiscalCode;
        this.prenotazioneConfermata=prenotazioneConfermata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public boolean getPrenotazioneConfermata() {
        return prenotazioneConfermata;
    }

    public void setPrenotazioneConfermata(boolean prenotazioneConfermata) {
        this.prenotazioneConfermata = prenotazioneConfermata;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}