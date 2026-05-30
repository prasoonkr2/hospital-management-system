package com.example.hospital_management.entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;

    private String hospitalName;

    private boolean active;

    private LocalTime openingTime;

    private LocalTime closingTime;

    // Constructors
    public Hospital() {
    }

    public Hospital(Long hospitalId, String hospitalName,
                    boolean active,
                    LocalTime openingTime,
                    LocalTime closingTime) {

        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.active = active;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    // Getters and Setters

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
}