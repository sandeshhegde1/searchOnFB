package com.example.sanh.facebook.Models;

/**
 * Created by San H on 4/24/2017.
 */
public class PostModel {

    String id;
    String name;
    String imageURL;
    String time;
    String message;
    Boolean fav;

    public String getName() {

        return name;
    }
    public void setName(String name) {

        this.name = name;
    }

    public String getID() {

        return id;
    }


    public void setID(String id) {

        this.id = id;
    }



    public String getImageURL() {
        return imageURL;
    }


    public void setImageURL(String imageURL) {

        this.imageURL = imageURL;
    }

    public Boolean getFav() {
        return fav;
    }


    public void setFav(Boolean fav) {

        this.fav =fav;
    }

    public String getTime() {
        return time;
    }


    public void setTime(String time) {

        this.time=time;
    }

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {

        this.message =message;
    }



    public PostModel(String id, String name, String imageURL,String time, String message,Boolean fav) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.id = id;
        this.imageURL = imageURL;
        this.fav=fav;
        this.time=time;
        this.message=message;
    }
}

