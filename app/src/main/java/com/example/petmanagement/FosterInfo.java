package com.example.petmanagement;

import java.io.Serializable;

public class FosterInfo implements Serializable {
    private int fosterId;
    private int petId;
    private String petName;
    private int duration;
    private int bathingTimes;
    private int totalCost;
    private String health;

    public FosterInfo(int fosterId, int petId, String petName, int duration, int bathingTimes, int totalCost, String health, String activity) {
        this.fosterId = fosterId;
        this.petId = petId;
        this.petName = petName;
        this.duration = duration;
        this.bathingTimes = bathingTimes;
        this.totalCost = totalCost;
        this.health = health;
        this.activity = activity;
    }

    public FosterInfo() {
    }

    public String getPetName() {
        return petName;
    }

    public String getActivity() {
        return activity;
    }

    private String activity;

    public int getFosterId() {
        return fosterId;
    }

    public int getPetId() {
        return petId;
    }

    public int getBathingTimes() {
        return bathingTimes;
    }

    public String getHealth() {
        return health;
    }

    public int getDuration() {
        return duration;
    }

    public int getTotalCost() {
        return totalCost;
    }

}
