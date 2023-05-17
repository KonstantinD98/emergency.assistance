package com.example.mergencyssistance.Entity;

public class Patient {
    int patientID;
    String firstNameP;
    String lastNameP;
    String phoneP;
    String emailP;
    int doctorID;

    public Patient(int patientID, String firstNameP, String lastNameP, String phoneP, String emailP, int doctorID) {
        this.patientID = patientID;
        this.firstNameP = firstNameP;
        this.lastNameP = lastNameP;
        this.phoneP = phoneP;
        this.emailP = emailP;
        this.doctorID = doctorID;
    }
    public Patient(){

    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getFirstNameP() {
        return firstNameP;
    }

    public void setFirstNameP(String firstNameP) {
        this.firstNameP = firstNameP;
    }

    public String getLastNameP() {
        return lastNameP;
    }

    public void setLastNameP(String lastNameP) {
        this.lastNameP = lastNameP;
    }

    public String getPhoneP() {
        return phoneP;
    }

    public void setPhoneP(String phoneP) {
        this.phoneP = phoneP;
    }

    public String getEmailP() {
        return emailP;
    }

    public void setEmailP(String emailP) {
        this.emailP = emailP;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
