package com.example.cinemashowcaseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemashowcaseapplication.adapter.MovieAdapter;
import com.example.cinemashowcaseapplication.model.Movie;
import com.example.cinemashowcaseapplication.util.ErrorHandler;
import com.example.cinemashowcaseapplication.util.JsonUtil;
import com.example.cinemashowcaseapplication.R;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerView moviesRecyclerView;
    private JsonUtil.MovieData movieData;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        loadMovieData();
    }

    private void initializeViews() {
        moviesRecyclerView = findViewById(R.id.recyclerViewMovies);

        // Sets up RecyclerView
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesRecyclerView.setHasFixedSize(true);
    }

    private void loadMovieData() {
        try {
            // It should load movie data from JSON file
            movieData = JsonUtil.loadMovieData(this, R.raw.movie_data);

            if (movieData == null) {
                throw new Exception("Failed to load movie data: data is null");
            }

            if (movieData.getMovies() == null) {
                throw new Exception("Failed to load movie data: movies list is null");
            }


            // Creates and sets up an adapter, what a joy!
            movieAdapter = new MovieAdapter(
                    this,
                    movieData.getMovies(),
                    this
            );
            moviesRecyclerView.setAdapter(movieAdapter);

        } catch (Exception e) {
            Log.e(TAG, "Error loading movie data", e);
            ErrorHandler.handleError(
                    this,
                    e,
                    "Failed to load movie data. Please try again later."
            );
        }
    }


    @Override
    public void onItemClick(int position) {
        try {
            Movie movie = movieAdapter.getMovie(position);
            if (movie != null) {
                // Launches the movie detail activity, if there is nothing showing up, then it displays N/A
                Intent intent = new Intent(this, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);

                intent.putExtra(MovieDetailActivity.EXTRA_CATEGORY_NAME,
                        getString(R.string.unknown_category));


                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling item click", e);
            ErrorHandler.handleError(
                    this,
                    e,
                    "Failed to open movie details. Please try again."
            );
        }
    }
}