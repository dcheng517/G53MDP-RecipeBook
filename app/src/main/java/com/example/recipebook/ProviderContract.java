package com.example.recipebook;

import android.net.Uri;

public class ProviderContract {

    public final static String AUTHORITY = "com.example.recipebook.provider.RecipeContentProvider";
    public final static String RECIPE_TABLE = "recipe";
    public final static Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(RECIPE_TABLE).build();

    public static final String R_ID = "r_id";
    public static final String R_NAME = "r_name";
    public static final String R_INGR = "r_ingr";
    public static final String R_STEPS = "r_steps";

}
