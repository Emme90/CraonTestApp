package com.example.craontestapp.model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("movie/popular?" + MovieApiService.API_KEY)
    Single<MovieSearch> getPopularMovies();

    @GET("movie/popular?" + MovieApiService.API_KEY)
    Single<MovieSearch> getNextPopularMovie(@Query("page") int page);

    @GET("movie/{id}?"+ MovieApiService.API_KEY)
    Single<Movie> getMovieById(@Path("id") int id);

    @GET("genre/movie/list?" + MovieApiService.API_KEY)
    Single<GenreSearch> getMovieGenres();
}
