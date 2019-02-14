package com.example.syedtahaalam.parkingsystem.DbContract;

public class Booking {
    private String UID;
    private String area;
    private String floor;
    private String  Hours;
    private String StartHour;
    private String StartMin;
    private String EndHour;
    private String EndMin;
    private String day;
    private String month;
    private String Year;

    public Booking() {
    }

    public Booking(String UID, String area, String hours, String startHour, String startMin, String endHour, String endMin, String day, String month, String year, String floor) {
        this.UID = UID;
        this.area = area;
        this.floor = floor;
        Hours = hours;
        StartHour = startHour;
        StartMin = startMin;
        EndHour = endHour;
        EndMin = endMin;
        this.day = day;
        this.month = month;
        Year = year;
    }

    public Booking(String UID, String area, String hours, String startHour, String startMin, String endHour, String endMin, String day, String month, String year) {
        this.UID = UID;
        this.area = area;
        Hours = hours;
        StartHour = startHour;
        StartMin = startMin;
        EndHour = endHour;
        EndMin = endMin;
        this.day = day;
        this.month = month;
        Year = year;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHours() {
        return Hours;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public String getStartHour() {
        return StartHour;
    }

    public void setStartHour(String startHour) {
        StartHour = startHour;
    }

    public String getStartMin() {
        return StartMin;
    }

    public void setStartMin(String startMin) {
        StartMin = startMin;
    }

    public String getEndHour() {
        return EndHour;
    }

    public void setEndHour(String endHour) {
        EndHour = endHour;
    }

    public String getEndMin() {
        return EndMin;
    }

    public void setEndMin(String endMin) {
        EndMin = endMin;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
