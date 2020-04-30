package com.odev.sinemaprojesi.Models;

public class Employee {
    public String employee_id;
    public String full_name;
    public String position;
    public int age;
    public int salary;

    public Employee(String employee_id, String full_name, String position, int age, int salary) {
        this.employee_id = employee_id;
        this.full_name = full_name;
        this.position = position;
        this.age = age;
        this.salary = salary;
    }

    public Employee() {
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "full_name='" + full_name + '\'' +
                ", position='" + position + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
