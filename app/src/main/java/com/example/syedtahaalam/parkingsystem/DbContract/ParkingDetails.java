package com.example.syedtahaalam.parkingsystem.DbContract;

import java.util.Random;

public class ParkingDetails {
    private String name;
    private String status;

    public ParkingDetails() {
    }

    public ParkingDetails(String name) {
        this.name = name;
        this.status = "free";
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        ParkingDetails o=(ParkingDetails)obj;
        return this.name.equals(o.getname())&&this.getstatus().equals("free");
    }

    @Override
    public int hashCode() {
        return this.getname()!=null? new Random().nextInt():0;
    }
}
