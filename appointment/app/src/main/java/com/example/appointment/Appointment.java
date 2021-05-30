package com.example.appointment;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Appointment {
    private String id;
    private String status;
    private String appointmentType;
    private String description;
    private String start;
    private String end;
    private String date;
    private int duration;
    private int slots;
    private List<Actor> actors;

    public Appointment() {
    }

    public Appointment(String status, String appointmentType, String reason,
                       String description, String start, String end, String date,
                       int slots) {
        this.id = UUID.randomUUID().toString();
        this.status = status;
        this.appointmentType = appointmentType;
        this.description = description;
        this.start = start;
        this.end = end;
        this.date = date;
        this.slots = slots;

        int endInt=0;
        int startInt=0;
        String[] arr= start.split(":");
        String[] arr2= end.split(":");
        if(arr.length==2){
            startInt=Integer.parseInt(arr[0])*60+Integer.parseInt(arr[1]);
        }
        if(arr2.length==2){
            endInt=Integer.parseInt(arr2[0])*60+Integer.parseInt(arr2[1]);
        }
        this.duration = endInt - startInt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void addActors(Actor actor) {
        this.actors.add(actor);
    }
}
