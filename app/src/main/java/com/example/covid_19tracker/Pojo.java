package com.example.covid_19tracker;

public class Pojo {

    private final String state;
    private final String active;
    private final String deceased;
    private final String recovered;

    public Pojo(String state, String active, String deceased, String recovered
                ) {
        this.state = state;
        this.active = active;
        this.deceased = deceased;
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public String getActive() {
        return active;
    }

    public String getDeceased() {
        return deceased;
    }

    public String getRecovered() {
        return recovered;
    }

}