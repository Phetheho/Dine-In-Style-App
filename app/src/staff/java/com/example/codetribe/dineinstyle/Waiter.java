package com.example.codetribe.dineinstyle;

/**
 * Created by Codetribe on 2017/02/22.
 */

public class Waiter {

    private String waiterName;
    private String waiterLastName;
    private String waiterGender;
    private String waiterDOB;
    private String waiterAge;
    private String workExperience;
    private String waiterEmail;

    public Waiter() {
    }


    public Waiter(String waiterName, String waiterEmail, String workExperience, String waiterAge, String waiterGender, String waiterDOB, String waiterLastName) {
        this.waiterName = waiterName;
        this.waiterEmail = waiterEmail;
        this.workExperience = workExperience;
        this.waiterAge = waiterAge;
        this.waiterDOB = waiterDOB;
        this.waiterGender = waiterGender;
        this.waiterLastName = waiterLastName;
    }


    public String getWaiterName() {
        return waiterName;
    }

    public String getWaiterGender() {
        return waiterGender;
    }

    public String getWaiterLastName() {
        return waiterLastName;
    }

    public String getWaiterAge() {
        return waiterAge;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public String getWaiterEmail() {
        return waiterEmail;
    }

    public String getWaiterDOB() {
        return waiterDOB;
    }
}
