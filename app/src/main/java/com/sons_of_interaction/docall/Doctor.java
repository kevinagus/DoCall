package com.sons_of_interaction.docall;

import java.util.ArrayList;

public class Doctor{

    private String name;
    private String surname;
    private String fiscalCode;
    private String birthDate;
    private String password;

    public Doctor(String name, String surname, String fiscalCode, String birthDate, String password) {

        this.name=name;
        this.surname=surname;
        this.fiscalCode=fiscalCode;
        this.birthDate=birthDate;
        this.password=password;

    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

}