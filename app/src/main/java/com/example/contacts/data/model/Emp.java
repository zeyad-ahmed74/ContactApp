package com.example.contacts.data.model;

public class Emp {

    private String jobTitle;
    private String e_mail;

    public Emp(String jobTitle, String e_mail) {
        this.jobTitle = jobTitle;
        this.e_mail = e_mail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
}
