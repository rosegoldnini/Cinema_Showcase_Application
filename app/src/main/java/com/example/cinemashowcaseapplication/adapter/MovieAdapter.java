package com.example.cinemashowcaseapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemashowcaseapplication.R;
import com.example.cinemashowcaseapplication.model.Category;
import com.example.cinemashowcaseapplication.model.Movie;
import com.example.cinemashowcaseapplication.util.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Constants for view types
    private static final int VIEW_TYPE_CATEGORY = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    // An interface for handling clicking items/movies!
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final Context context;
    private final List<Object> items; // Combined list of categories and menu items
    private final OnItemClickListener listener;

    //More constructors, yay!
    public MovieAdapter(Context context, List<Movie> movies, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.items = new ArrayList<>();
        if (movies != null) {
            this.items.addAll(movies);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Since we only have movies now (no categories), it will always return VIEW_TYPE_ITEM
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_CATEGORY) {
            View view = inflater.inflate(R.layout.movie_category_header, parent, false);
            return new MovieViewHolder.CategoryViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.cinema_menu, parent, false);
            return new MovieViewHolder.ItemViewHolder(view, listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof MovieViewHolder.ItemViewHolder) {
                Movie movie = (Movie) items.get(position);
                MovieViewHolder.ItemViewHolder itemHolder = (MovieViewHolder.ItemViewHolder) holder;

                // Safely sets the text by checking text views are not null
                if (itemHolder.getMovieNameTextView() != null) {
                    itemHolder.getMovieNameTextView().setText(movie.getName());
                }

                if (itemHolder.getMovieGenreTextView() != null) {
                    itemHolder.getMovieGenreTextView().setText(movie.getGenre());
                }

                if (itemHolder.getMovieYearTextView() != null) {
                    itemHolder.getMovieYearTextView().setText(movie.getFormattedYear());
                }

                if (itemHolder.getMovieDescriptionTextView() != null) {
                    String description = movie.getDescription();
                    if (description != null && description.length() > 50) {
                        description = description.substring(0, 47) + "...";
                    }
                    itemHolder.getMovieDescriptionTextView().setText(description);
                }

                // Load image safely
                if (itemHolder.getMovieImageView() != null) {
                    try {
                        int imageResourceId = ErrorHandler.getDrawableResourceId(
                                context, movie.getImageFileName(), android.R.drawable.ic_menu_gallery);
                        itemHolder.getMovieImageView().setImageResource(imageResourceId);
                    } catch (Exception e) {
                        itemHolder.getMovieImageView().setImageResource(R.drawable.placeholder_movie);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("MovieAdapter", "Error binding view at position " + position, e);
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    //Gets a movie from a certain position
    public Movie getMovie(int position) {
        if (position >= 0 && position < items.size()) {
            Object item = items.get(position);
            if (item instanceof Movie) {
                return (Movie) item;
            }
        }
        return null;
    }
}