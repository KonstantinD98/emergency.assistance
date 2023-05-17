package com.example.mergencyssistance.Entity;

public class Vet {
    int vetID;
    String firstNameV;
    String lastNameV;
    String emailV;
    String phoneV;

    public Vet(int vetID, String firstNameV, String lastNameV, String emailV, String phoneV) {
        this.vetID = vetID;
        this.firstNameV = firstNameV;
        this.lastNameV = lastNameV;
        this.emailV = emailV;
        this.phoneV = phoneV;
    }
    public Vet(){

    }

    public int getVetID() {
        return vetID;
    }

    public void setVetID(int vetID) {
        this.vetID = vetID;
    }

    public String getFirstNameV() {
        return firstNameV;
    }

    public void setFirstNameV(String firstNameV) {
        this.firstNameV = firstNameV;
    }

    public String getLastNameV() {
        return lastNameV;
    }

    public void setLastNameV(String lastNameV) {
        this.lastNameV = lastNameV;
    }

    public String getEmailV() {
        return emailV;
    }

    public void setEmailV(String emailV) {
        this.emailV = emailV;
    }

    public String getPhoneV() {
        return phoneV;
    }

    public void setPhoneV(String phoneV) {
        this.phoneV = phoneV;
    }
}
