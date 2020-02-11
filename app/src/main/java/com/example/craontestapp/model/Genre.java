package com.example.craontestapp.model;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String type;

    public Genre(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
