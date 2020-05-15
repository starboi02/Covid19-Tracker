package com.example.covid_19tracker;

public class ListItems {
    private String state;
    private String active;
    private String recovered;
    private String dead;
    private String inc_active;
    private String inc_recovered;
    private String inc_deceased;

    public ListItems( String state,String active,String recovered,String dead,String inc_active,String inc_recovered,String inc_deceased){
        this.state = state;
        this.active=active;
        this.recovered=recovered;
        this.dead=dead;
        this.inc_active= inc_active;
        this.inc_recovered=inc_recovered;
        this.inc_deceased=inc_deceased;
    }
    public String getState(){
        return state;
    }
    public String getActive(){
        return active;
    }
    public String getRecovered(){
        return recovered;
    }
    public String getDead(){
        return dead;
    }
    public String getInc_active(){
        return inc_active;
    }
    public String getInc_recovered(){
        return inc_recovered;
    }
    public String getInc_deceased(){
        return inc_deceased;
    }
}
