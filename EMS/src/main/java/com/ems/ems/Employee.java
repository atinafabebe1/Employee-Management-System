package com.ems.ems;

public class Employee {

    private int id;
    private String firstname;
    private String lastname;
    private int employee_no;
    private String phoneNumber;
    private String department_name;
    private String position_name;
    private double salary;

    public Employee(int id, String firstname, String lastname, int employee_no, String phoneNumber, String department_name, String position_name, double salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.employee_no = employee_no;
        this.phoneNumber = phoneNumber;
        this.department_name = department_name;
        this.position_name = position_name;
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

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", employee_no=" + employee_no +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department_name='" + department_name + '\'' +
                ", position_name='" + position_name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
