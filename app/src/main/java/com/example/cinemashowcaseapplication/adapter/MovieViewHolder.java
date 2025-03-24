package com.example.cinemashowcaseapplication.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemashowcaseapplication.R;

public class MovieViewHolder {

   //Category header view
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.textViewCategoryName);
        }

        public void bind(String categoryName) {
            categoryNameTextView.setText(categoryName);
        }
    }

    //Menu view
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView movieImageView;
        private final TextView movieNameTextView;
        private final TextView movieYearTextView;
        private final TextView movieDescriptionTextView;
        private final TextView movieGenreTextView;

        public ItemViewHolder(@NonNull View itemView, final MovieAdapter.OnItemClickListener listener) {
            super(itemView);

            movieImageView = itemView.findViewById(R.id.imageViewMovie);
            movieNameTextView = itemView.findViewById(R.id.textViewMovieName);
            movieYearTextView = itemView.findViewById(R.id.textViewMovieYear);
            movieGenreTextView = itemView.findViewById(R.id.textViewCategoryName);
            movieDescriptionTextView = itemView.findViewById(R.id.textViewMovieDescription);

            // Sets the click listener for the entire item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public ImageView getMovieImageView() {
            return movieImageView;
        }

        public TextView getMovieNameTextView() {
            return movieNameTextView;
        }

        public TextView getMovieYearTextView() {
            return movieYearTextView;
        }
        public TextView getMovieGenreTextView() {
            return movieGenreTextView;
        }

        public TextView getMovieDescriptionTextView() {
            return movieDescriptionTextView;
        }
    }

}
