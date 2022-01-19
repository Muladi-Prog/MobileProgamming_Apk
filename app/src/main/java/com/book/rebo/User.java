package com.book.rebo;

public class User {
    private String userName;
    private String phoneNumber;
    private String password;
    private String gender;
    private String DOB;
    private String address;

    public User(String userName, String phoneNumber, String password, String gender, String DOB, String address) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
