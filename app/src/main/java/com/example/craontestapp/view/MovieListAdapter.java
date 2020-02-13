package com.example.craontestapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.craontestapp.R;
import com.example.craontestapp.databinding.MovieListItemBinding;
import com.example.craontestapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> implements View.OnClickListener {

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

        holder.itemView.movieItemRelativeLayout.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onClick(View v) {
        String movieId = ((TextView)v.findViewById(R.id.movieListId)).getText().toString();
        int mId = Integer.valueOf(movieId);
        String movieTitle = ((TextView)v.findViewById(R.id.movieTitle)).getText().toString();

        NavDirections action = MovieListFragmentDirections.actionMovieDetail();
        ((MovieListFragmentDirections.ActionMovieDetail)action).setMovieId(mId);
        ((MovieListFragmentDirections.ActionMovieDetail)action).setMovieTitle(movieTitle);

        Navigation.findNavController(v).navigate(action);

    }


    class MovieViewHolder extends RecyclerView.ViewHolder{

        public MovieListItemBinding itemView;

        public MovieViewHolder(@NonNull MovieListItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
