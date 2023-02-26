package com.ems.ems;

//AttendanceRecord class
public class AttendanceRecord {
    private int id;
    private int employeeId;
    private String date;
    private String status;

    public AttendanceRecord(int id, int employeeId, String date, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
