package com.example.craontestapp.model;

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
    public String duration;
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

    public Movie(int movieId, String imdbId, String title, String releaseDate, String duration, List<Genre> genre, String website, String plot, String slogan, List<Language> languages, String imageUrl) {
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
}
