package com.example.craontestapp.model;

import com.example.craontestapp.util.Util;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("id")
    public int movieId;
    @SerializedName("imdb_id")
    public String imdbId;
    public String title;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("runtime")
    public int duration;
    @SerializedName("genres")
    public List<Genre> genre;
    @SerializedName("genre_ids")
    public int[] genreIds;
    @SerializedName("homepage")
    public String website;
    @SerializedName("overview")
    public String plot;
    @SerializedName("tagline")
    public String slogan;
    public List<Language> languages;
    @SerializedName("poster_path")  // may be null!
    public String imageUrl;

    public boolean websiteExist = false;

    public Movie(int movieId, String imdbId, String title, String releaseDate, int duration, List<Genre> genre, String website, String plot, String slogan, List<Language> languages, String imageUrl) {
        this.movieId = movieId;
        this.imdbId = imdbId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.website = website;
        this.plot = plot;
        this.slogan = slogan;
        this.languages = languages;
        this.imageUrl = imageUrl;
    }

    public String getFormattedDuration() {
        String formattedDuration = Util.convertTimeToDuration(duration);
        return formattedDuration;
    }

    public String getFormattedDate() {
        String formattedDate = Util.formattingDate(releaseDate);
        return formattedDate;
    }

    public String getExistingWebsite() {
        if (website == null || website.equals("")) {
            websiteExist = false;
            return website;
        } else {
            websiteExist = true;
            return website;
        }
    }

    public String getGenres() {
        String genres = "";
        for (int i = 0; i < genre.size(); i++) {
            if (i == genre.size() - 1) {
                genres = genres + genre.get(i).type;
            } else {
                genres = genres + genre.get(i).type + " | ";
            }
        }
        return genres;
    }
}
