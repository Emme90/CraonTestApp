package com.example.craontestapp.model;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiService {

    /* THE MOVIE DB API
     * documentation: https://developers.themoviedb.org/3/getting-started/introduction
     * API settings: https://www.themoviedb.org/settings/api
     * username: emme90
     * psw: 31051990
     *
     * requestExemple: https://api.themoviedb.org/3/movie/550?api_key=821698dbb5d3bfb11bc1cdeeade38964
     */

    public static final String API_KEY = "api_key=821698dbb5d3bfb11bc1cdeeade38964";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private MovieApi movieApi;

    public MovieApiService() {
        movieApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieApi.class);
    }

    public Single<MovieSearch> getPopularMovies() {
        return movieApi.getPopularMovies();
    }

    public Single<Movie> getMovieById(int id) {
        return movieApi.getMovieById(id);
    }

    public Single<GenreSearch> getAllGenres() {
        return movieApi.getMovieGenres();
    }

    public Single<MovieSearch> getNextPopularMovie(int currentPage) {
        return movieApi.getNextPopularMovie(currentPage);
    }
}
