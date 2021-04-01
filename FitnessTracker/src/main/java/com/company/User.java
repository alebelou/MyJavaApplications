package com.company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
    private String login;
    private Double totalCalories = 0.0;
    private Double lastCalories = 0.0;

    @XmlElement
    public String getLogin() {
        return login;
    }

    @XmlElement
    public Double getLastCalories() {
        return lastCalories;
    }

    @XmlElement
    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setLastCalories(Double lastCalories) {
        this.lastCalories = lastCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }


}
