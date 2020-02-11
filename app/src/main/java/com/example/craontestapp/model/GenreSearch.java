package com.example.craontestapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreSearch {

    @SerializedName("genres")
    public List<Genre> geners;

    public GenreSearch(List<Genre> geners) {
        this.geners = geners;
    }
}
