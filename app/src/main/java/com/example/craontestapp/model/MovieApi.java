package com.example.craontestapp.model;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieApi {

    @GET("movie/popular?" + MovieApiService.API_KEY)
    Single<MovieSearch> getPopularMovies();

    @GET("movie/:id?"+ MovieApiService.API_KEY)
    Single<Movie> getMovieById(int id);

    @GET("genre/movie/list?" + MovieApiService.API_KEY)
    Single<GenreSearch> getMovieGenres();
}