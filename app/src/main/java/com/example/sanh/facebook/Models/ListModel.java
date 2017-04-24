package com.example.sanh.facebook.Models;

/**
 * Created by San H on 4/23/2017.
 */
public class ListModel {

    String id;
    String name;
    String imageURL;
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



    public ListModel(String id, String name, String imageURL,Boolean fav) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.id = id;
        this.imageURL = imageURL;
        this.fav=fav;
    }
}

