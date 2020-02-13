package com.example.craontestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.craontestapp.model.Movie;
import com.example.craontestapp.model.MovieApiService;
import com.example.craontestapp.model.MovieSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Movie>> movies = new MutableLiveData<>(new ArrayList<>(0));
    public MutableLiveData<Boolean> movieLoadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private int pageNumber = 1;
    private int totalPageNumber = 1;

    private MovieApiService movieApiService = new MovieApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public ListViewModel(@NonNull Application application) {
        super(application);
        fetchFromRemote(pageNumber);
//        MediatorLiveData<Integer> mediator = new MediatorLiveData<>();
//        mediator.addSource(pageNumber, this::fetchFromRemote);
    }

    public void fetchData() {
        int page = pageNumber;
        if (page < totalPageNumber){
            page++;
            pageNumber = page;
            fetchFromRemote(page);
        }
    }

    private void fetchFromRemote(int page) {
        if (page == 1) {
            loading.setValue(true);
        }
        disposable.add(
                movieApiService.getNextPopularMovie(page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MovieSearch>() {
                            @Override
                            public void onSuccess(MovieSearch movieSearch) {
                                movieRetrieve(movieSearch.result);
                                totalPageNumber = movieSearch.totalPage;
                            }

                            @Override
                            public void onError(Throwable e) {
                                movieLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();

                            }
                        })
        );
    }
//
//    public void fetchNextData() {
//        loading.setValue(true);
//        disposable.add(
//                movieApiService.getNextPopularMovie(pageNumber)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<MovieSearch>() {
//                            @Override
//                            public void onSuccess(MovieSearch movieSearch) {
//                                movieRetrieve(movieSearch.result);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//                        })
//        );
//        //return currentPage;
//    }

    private void movieRetrieve(List<Movie> movieList) {
        movies.getValue().addAll(movieList);
        movies.setValue(movies.getValue());
        movieLoadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
