package com.example.appointment;

import java.time.LocalDateTime;
import java.util.List;

public class Appointment {
    private int id;
    private String status;
    private String ServiceType;
    private String appointmentType;
    private String reason;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int duration;
    private int slots;
    private List<Patient> participants;
    private Actor actor;




}
