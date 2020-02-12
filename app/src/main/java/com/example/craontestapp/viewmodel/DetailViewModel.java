package com.example.craontestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.craontestapp.model.Movie;
import com.example.craontestapp.model.MovieApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends AndroidViewModel {

    public MutableLiveData<Movie> movieLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> movieDetailLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> movieDetailError = new MutableLiveData<>();

    private MovieApiService movieApiService = new MovieApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchMovieById(int id){
        movieDetailLoading.setValue(true);
        disposable.add(
                movieApiService.getMovieById(id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Movie>() {
                            @Override
                            public void onSuccess(Movie movie) {
                                retrieveMovie(movie);
                            }

                            @Override
                            public void onError(Throwable e) {
                                movieDetailLoading.setValue(false);
                                movieDetailError.setValue(true);
                                e.printStackTrace();
                            }
                        })
        );

    }

    private void retrieveMovie(Movie movie) {
        movieLiveData.setValue(movie);
        movieDetailLoading.setValue(false);
        movieDetailError.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
