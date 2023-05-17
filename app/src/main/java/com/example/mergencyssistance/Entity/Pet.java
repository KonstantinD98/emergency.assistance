package com.example.mergencyssistance.Entity;

public class Pet {
    int petID;
    String animal;
    String breed;
    String name;
    String ownerName;
    int vetID;

    public Pet(int petID, String animal, String breed, String name, String ownerName, int vetID) {
        this.petID = petID;
        this.animal = animal;
        this.breed = breed;
        this.name = name;
        this.ownerName = ownerName;
        this.vetID = vetID;
    }
    public Pet(){

    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getVetID() {
        return vetID;
    }

    public void setVetID(int vetID) {
        this.vetID = vetID;
    }
}
