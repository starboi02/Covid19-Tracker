package com.example.covid_19tracker;

public class ListItems {
    private String state;
    private String active;
    private String dead;
    private String recovered;

    public ListItems( String state,String active,String dead,String recovered){
        this.state = state;
        this.active=active;
        this.dead=dead;
        this.recovered=recovered;
    }
    public String getState(){
        return state;
    }
    public String getActive(){
        return active;
    }
    public String getDead(){
        return dead;
    }
    public String getRecovered(){
        return recovered;
    }
}
