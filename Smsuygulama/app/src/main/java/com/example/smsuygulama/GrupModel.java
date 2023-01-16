package com.example.smsuygulama;

import java.util.List;

public class GrupModel {
    private String name, description, image, uid;
    private List<String> number;
    public GrupModel(){

    }

    public GrupModel(String name, String description, String image, List<String> number, String uid){
        this.name = name;
        this.description = description;
        this.image = image;
        this.number = number;
        this.uid = uid;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public List<String> getNumber(){
        return number;
    }
    public void setNumber(List<String> number){
        this.number = number;
    }
    public String getUid(){
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }
}
