package com.example.covid_19tracker;

public class CatItems {
    private String category;
    private String name;
    private String description;
    private String phoneNumber;
    private String link;


    public CatItems( String category,String name,String description,String phoneNumber,String link){
        this.category = category;
        this.name=name;
        this.description=description;
        this.phoneNumber=phoneNumber;
        this.link=link;
    }
    public String getCategory(){
        return category;
    }
    public String getname(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getLink(){ return link;}
}
