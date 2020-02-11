package com.example.craontestapp.model;

import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("iso_639_1")
    public String shortName;
    @SerializedName("name")
    public String language;

    public Language(String shortName, String language) {
        this.shortName = shortName;
        this.language = language;
    }
}
