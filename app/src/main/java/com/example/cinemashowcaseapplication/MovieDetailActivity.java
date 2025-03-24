package com.example.cinemashowcaseapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemashowcaseapplication.model.Movie;
import com.example.cinemashowcaseapplication.util.ErrorHandler;
import com.example.cinemashowcaseapplication.R;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailActivity";

    // Intent extras
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_CATEGORY_NAME = "extra_category_name";

    private ImageView movieImageView;
    private TextView movieNameTextView;
    private TextView movieYearTextView;
    private TextView movieCategoryTextView;
    private TextView movieDescriptionTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initializeViews();

        displayMovieDetails();

        // How to set up a back button to go back to the showcase
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // UI components!
    private void initializeViews() {
        movieImageView = findViewById(R.id.imageViewDetail);
        movieNameTextView = findViewById(R.id.textViewMovieName);
        movieYearTextView = findViewById(R.id.textViewMovieYear);
        movieCategoryTextView = findViewById(R.id.textViewMovieCategory);
        movieDescriptionTextView = findViewById(R.id.textViewDetailDescription);
        backButton = findViewById(R.id.buttonBack);
    }


    private void displayMovieDetails() {
        try {
            // Get movie from intent
            Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
            String categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);

            if (movie == null) {
                throw new IllegalArgumentException("Movie not found");
            }

            // Displays movie details, mainly the image, name, year, description and genre
            movieNameTextView.setText(movie.getName());
            movieYearTextView.setText(movie.getFormattedYear());
            movieDescriptionTextView.setText(movie.getDescription());
            movieCategoryTextView.setText(movie.getGenre());

            // Loads the image
            try {
                int imageResourceId = ErrorHandler.getDrawableResourceId(
                        this, movie.getImageFileName(), android.R.drawable.ic_menu_gallery);
                movieImageView.setImageResource(imageResourceId);
            } catch (Exception e) {
                // If that doesn't work, show a placeholder
                movieImageView.setBackgroundColor(
                        getResources().getColor(android.R.color.darker_gray));
                Log.e(TAG, "Error loading image", e);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error displaying movie details", e);
            ErrorHandler.handleError(
                    this,
                    e,
                    "Failed to display movie details. Please try again."
            );
            //No details to display? That is fine, just go back to the main activity
            finish();
        }
    }
}