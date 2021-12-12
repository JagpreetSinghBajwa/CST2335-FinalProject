package com.example.cst2335_finalproject;


public class NewsInformation  {
    private Long Id;
    private String headline;
    private  String description;
    private String link;
    private String date;

    public NewsInformation(){

    }
    public NewsInformation(String headline, String description, String link, String date) {
        this.headline=headline;
        this.description=description;
        this.link=link;
        this.date=date;
    }
    public NewsInformation(Long Id, String headline, String description, String link, String date) {
        this.Id=Id;
        this.headline=headline;
        this.description=description;
        this.link=link;
        this.date=date;
    }
    //getter and setter methods
    public Long getId(){ return Id; }

    public void setId(Long Id){this.Id=Id;}

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate(){ return date; }

    public void setDate(String date){ this.date=date; }




}