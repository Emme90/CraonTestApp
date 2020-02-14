package com.example.craontestapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.craontestapp.R;
import com.example.craontestapp.databinding.FragmentMovieDetailBinding;
import com.example.craontestapp.model.Movie;
import com.example.craontestapp.model.MoviePalette;
import com.example.craontestapp.util.ImageUtil;
import com.example.craontestapp.viewmodel.DetailViewModel;

public class MovieDetailFragment extends Fragment {

    private int movieId;
    private FragmentMovieDetailBinding mBinding;
    private DetailViewModel viewModel;
    private Movie currentMovie;

    public MovieDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMovieDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        this.mBinding = binding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            movieId = MovieDetailFragmentArgs.fromBundle(getArguments()).getMovieId();
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.fetchMovieById(movieId);

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.movieLiveData.observe(this.getViewLifecycleOwner(), movie -> {
            if (movie != null && movie instanceof Movie) {
                currentMovie = movie;
                mBinding.setMovie(movie);
                if (movie.imageUrl != null) {
                    // utilizzo di palette
                    setUpBackgroundColor(ImageUtil.IMAGE_BASE_URL + movie.imageUrl);
                }
            }
        });
        viewModel.movieDetailLoading.observe(this.getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null && isLoading instanceof Boolean) {
                mBinding.movieDetailProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    mBinding.movieDetailLoadError.setVisibility(View.GONE);
                }
            }
        });
        viewModel.movieDetailError.observe(this.getViewLifecycleOwner(), isError -> {
            if (isError != null && isError instanceof Boolean) {
                mBinding.movieDetailLoadError.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void setUpBackgroundColor(String url){
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource)
                                .generate(palette -> {
                                    if (palette.getLightMutedSwatch() != null){
                                        int color = palette.getLightMutedSwatch().getRgb();
                                        MoviePalette moviePalette = new MoviePalette(color);
                                        mBinding.setPalette(moviePalette);
                                    }
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
