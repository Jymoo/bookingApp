package com.example.bookapp;

public class userData {
    private String userName, jobName, memail, phone, location;

    public userData(String userName, String jobName, String memail, String phone, String location, String s) {
        this.userName = userName;
        this.jobName = jobName;
        this.memail = memail;
        this.phone = phone;
        this.location = location;
    }

    public userData(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getMemail() {
        return memail;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "userData{" +
                "userName='" + userName + '\'' +
                ", jobName='" + jobName + '\'' +
                ", memail='" + memail + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}