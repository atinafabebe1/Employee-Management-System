package com.ems.ems;

public class Employee {

    private int id;
    private String firstname;
    private String lastname;
    private int employee_no;
    private String phoneNumber;
    private String departmentName;
    private String positionName;
    private double salary;

    public Employee(int id, String firstname, String lastname, int employee_no, String phoneNumber, String departmentName, String positionName, double salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.employee_no = employee_no;
        this.phoneNumber = phoneNumber;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.salary = salary;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(int employee_no) {
        this.employee_no = employee_no;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
