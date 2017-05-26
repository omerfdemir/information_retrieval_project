package com.example.omerfdemir.msku_aps;

/**
 * Created by omerfdemir on 15.05.2017.
 */

public class Instructor {
    private String name;
    private String image_location;
    public Instructor(String name,String image_location){
        this.setName(name);
        this.setImageLocation(image_location);
    }
    public String getName(){
        return name;
    }
    public String getImageLocation(){
        return image_location;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setImageLocation(String image_location){
        this.image_location = image_location;
    }


}
