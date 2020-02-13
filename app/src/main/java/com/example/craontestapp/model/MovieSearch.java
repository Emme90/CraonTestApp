package com.example.craontestapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSearch {

    @SerializedName("page")
    public int page;
    @SerializedName("total_results")
    public int totalResult;
    @SerializedName("total_pages")
    public int totalPage;
    @SerializedName("results")
    public List<Movie> result;

    public MovieSearch(int page, int totalResult, int totalPage, List<Movie> result) {
        this.page = page;
        this.totalResult = totalResult;
        this.totalPage = totalPage;
        this.result = result;
    }
}
