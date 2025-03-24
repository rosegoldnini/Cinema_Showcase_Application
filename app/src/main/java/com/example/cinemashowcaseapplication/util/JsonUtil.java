package com.example.cinemashowcaseapplication.util;

import android.content.Context;
import android.util.Log;

import com.example.cinemashowcaseapplication.model.Category;
import com.example.cinemashowcaseapplication.model.Movie;
import com.example.cinemashowcaseapplication.model.Cinema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final String TAG = "JsonUtil";

    // Class loads, reads and parses important data from a JSON file
    public static MovieData loadMovieData(Context context, int resourceId) throws IOException, JSONException {
        // Basically reading from a JSON file, just a similar syntax to Python tbh..
        String jsonContent = readJsonFile(context, resourceId);

        // Parse the JSON content
        JSONArray jsonArray = new JSONArray(jsonContent);

        List<Movie> movies = new ArrayList<>();

        // Creates the MovieData container
        MovieData movieData = new MovieData();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieJson = jsonArray.getJSONObject(i);
            Movie movie = new Movie();
            if (movieJson.has("title")) {
                if(!movieJson.getString("title").equals("null")) {
                    movie.setName(movieJson.getString("title"));
                }
                else {
                    movie.setName("N/A");
                }
            } else {
                movie.setName("N/A");
            }

            if (movieJson.has("genre")) {
                movie.setGenre(movieJson.getString("genre"));
            } else {
                movie.setGenre("N/A");
            }

            if (movieJson.has("description")) {
                movie.setDescription(movie.getGenre() + " movie");
            } else {
                movie.setDescription(movie.getGenre() + " movie");
            }

            if (movieJson.has("year")) {
                try {
                    movie.setYear(Integer.parseInt(movieJson.getString("year")));
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Non-numeric year format: " + movieJson.getString("year"));
                    movie.setYear(-1);
                } catch (JSONException e) {
                    movie.setYear(-1);
                }
            } else {
                movie.setYear(-1);
            }

            if (movieJson.has("genre")) {
                movie.setGenre(movieJson.getString("genre"));
            } else {
                movie.setGenre("N/A");
            }

            if (movieJson.has("poster")) {
                movie.setImageFileName(movieJson.getString("poster"));
            } else {
                movie.setImageFileName("placeholder_movie");
            }
            movies.add(movie);
        }
        movieData.setMovies(movies);
        return movieData;
    }

    // A certain helper method to read the file from resources
    private static String readJsonFile(Context context, int resourceId) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = context.getResources().openRawResource(resourceId);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading JSON file", e);
            throw e;
        }
        return stringBuilder.toString();
    }

    // Container class for all movie data, such as categories, movie and cinema
    public static class MovieData {
        private Cinema cinema;
        private List<Category> categories;
        private List<Movie> movies;

        public Cinema getCinema() {
            return cinema;
        }

        public void setCinema(Cinema cinema) {
            this.cinema = cinema;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public List<Movie> getMovies() {
            return movies;
        }

        public void setMovies(List<Movie> movies) {
            this.movies = movies;
        }

        // Get movies for a specific category
        public List<Movie> getMoviesByCategory(int categoryId) {
            List<Movie> items = new ArrayList<>();
            if (movies != null) {
                for (Movie movie : movies) {
                    if (movie.getCategoryId() == categoryId) {
                        items.add(movie);
                    }
                }
            }
            return items;
        }
    }
}