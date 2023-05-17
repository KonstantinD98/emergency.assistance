package com.example.mergencyssistance.Entity;

public class Doctor {
    int doctorID;
    String firstNameD;
    String lastNameD;
    String emailD;
    String phone;
    String speciality;

    public Doctor(int doctorID, String firstNameD, String lastNameD, String emailD, String phone, String speciality) {
        this.doctorID = doctorID;
        this.firstNameD = firstNameD;
        this.lastNameD = lastNameD;
        this.emailD = emailD;
        this.phone = phone;
        this.speciality = speciality;
    }
    public Doctor() {

    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getFirstNameD() {
        return firstNameD;
    }

    public void setFirstNameD(String firstNameD) {
        this.firstNameD = firstNameD;
    }

    public String getLastNameD() {
        return lastNameD;
    }

    public void setLastNameD(String lastNameD) {
        this.lastNameD = lastNameD;
    }

    public String getEmailD() {
        return emailD;
    }

    public void setEmailD(String emailD) {
        this.emailD = emailD;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
