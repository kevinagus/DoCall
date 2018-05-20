package com.sons_of_interaction.docall;


public class Person {

    private String name;
    private String surname;
    private String birthDate;
    private String password;
    private String fiscalCode;
    private Doctor doctor;

    public Person(String name,String surname,String birthDate,String password,String fiscalCode,Doctor doctor) {
        this.name=name;
        this.surname=surname;
        this.birthDate=birthDate;
        this.password=password;
        this.fiscalCode=fiscalCode;
        this.doctor=doctor;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}