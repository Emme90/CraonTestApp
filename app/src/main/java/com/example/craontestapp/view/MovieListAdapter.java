package com.example.craontestapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.craontestapp.R;
import com.example.craontestapp.databinding.MovieListItemBinding;
import com.example.craontestapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieList;

    public MovieListAdapter(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public void updateMovieList(List<Movie> newList){
        movieList.clear();
        movieList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieListItemBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.itemView.setMovie(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        public MovieListItemBinding itemView;

        public MovieViewHolder(@NonNull MovieListItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
