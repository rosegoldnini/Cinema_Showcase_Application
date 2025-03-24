package com.example.cinemashowcaseapplication.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ErrorHandler {
    private static final String TAG = "ErrorHandler";

    //How to handle and display specific errors and its types
    public static boolean handleError(@NonNull Context context, @Nullable Throwable throwable, String fallbackMessage) {
        if (throwable == null) {
            showError(context, fallbackMessage);
            return true;
        }

        Log.e(TAG, "Error occurred: " + throwable.getMessage(), throwable);

        if (throwable instanceof FileNotFoundException) {
            showError(context, "Required file was not found. Please reinstall the app.");
            return true;
        } else if (throwable instanceof JSONException) {
            showError(context, "Error reading menu data. Data format is invalid.");
            return true;
        } else if (throwable instanceof IOException) {
            showError(context, "Error accessing menu data. Please try again.");
            return true;
        } else if (throwable instanceof Resources.NotFoundException) {
            showError(context, "Required resource was not found. Please reinstall the app.");
            return true;
        } else if (throwable instanceof NullPointerException) {
            showError(context, "An unexpected error occurred. Please try again.");
            return true;
        } else if (throwable instanceof IndexOutOfBoundsException) {
            showError(context, "Invalid data index. Please report this issue.");
            return true;
        } else if (throwable instanceof IllegalArgumentException) {
            showError(context, "Invalid operation. Please report this issue.");
            return true;
        }

        // Use fallback for unhandled exceptions
        showError(context, fallbackMessage);
        return false;
    }

    //Showing the error message
    public static void showError(@NonNull Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //Logging the error message
    public static void logError(String tag, String message, @Nullable Throwable throwable) {
        if (throwable != null) {
            Log.e(tag, message, throwable);
        } else {
            Log.e(tag, message);
        }
    }

    //Get a resource ID from a string name
    public static int getDrawableResourceId(Context context, String resourceName, int defaultResourceId) {
        try {
            if (resourceName == null || resourceName.isEmpty()) {
                return defaultResourceId;
            }

            int resourceId = context.getResources().getIdentifier(
                    resourceName, "drawable", context.getPackageName());

            if (resourceId == 0) {
                Log.w(TAG, "Resource not found: " + resourceName);
                return defaultResourceId;
            }

            // Aaand check if the default resource exists!
            if (defaultResourceId != 0) {
                try {
                    context.getResources().getResourceName(defaultResourceId);
                } catch (Resources.NotFoundException e) {
                    Log.w(TAG, "Default resource not found: " + defaultResourceId);
                    // Create a color drawable as a last resort
                    return android.R.color.darker_gray;
                }
            }

            return resourceId;
        } catch (Exception e) {
            Log.e(TAG, "Error finding resource: " + resourceName, e);
            return defaultResourceId;
        }
    }
}
