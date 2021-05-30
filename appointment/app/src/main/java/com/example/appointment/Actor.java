package com.example.appointment;

import java.util.UUID;

public class Actor {

    private String uid;
    private String email;
    private boolean active;
    private String type; //Patient | Practitioner | PractitionerRole | RelatedPerson | Device | HealthcareService | Location
    private String name;
    private String phoneNumber;
    private String gender;
    private String birthDate;

    public Actor() {
    }

    public Actor(String uid,String email, boolean active, String type, String name, String phoneNumber, String gender, String birthDate) {
        this.uid = uid;
        this.email = email;
        this.active = active;
        this.type = type;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
