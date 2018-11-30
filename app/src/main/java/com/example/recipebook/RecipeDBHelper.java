package com.example.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RecipeDBHelper extends SQLiteOpenHelper {

    public static final String log = "G53MDP";

    private ContentResolver myCR;

    private static final int DB_VER = 1;
    private static final String DB_NAME = "recipe.db";

    public static final String TABLE = "recipe";
    public static final String COL_UID = "r_id";
    public static final String COL_NAME = "r_name";
    public static final String COL_INGR = "r_ingr";
    public static final String COL_STEPS = "r_steps";

    private static final String sql_create =
            "CREATE TABLE if not exists " + TABLE + " (" +
                    COL_UID + " INTEGER PRIMARY KEY, " +
                    COL_NAME + " TEXT, " +
                    COL_INGR + " TEXT, " +
                    COL_STEPS + " TEXT);";

    public RecipeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DB_NAME, factory, DB_VER);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(log, "Creating table.");
        db.execSQL(sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(log, "Upgrading table");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);

    }

//    returns a list of all recipes
    public List<Recipe> allRecipes()
    {
        List<Recipe> recipeList = new ArrayList<>();

        String[] projection = {COL_UID, COL_NAME, COL_INGR, COL_STEPS};
        String selection = null;

        int id;
        String name;
        String ingr;
        String steps;

        Cursor cursor = myCR.query(ProviderContract.CONTENT_URI, projection, selection,
                null, null);

        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    id = cursor.getInt(cursor.getColumnIndex(COL_UID));
                    name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                    ingr = cursor.getString(cursor.getColumnIndex(COL_INGR));
                    steps = cursor.getString(cursor.getColumnIndex(COL_STEPS));

                    Recipe res = new Recipe(id, name, ingr, steps);
                    recipeList.add(res);
                }while (cursor.moveToNext());
            }
        }

        return recipeList;
    }

    //    Search recipe by name
    public List<Recipe> queryRecipe (String name)
    {
        List<Recipe> searchList = new ArrayList<>();

        String[] projection = {COL_UID, COL_NAME, COL_INGR, COL_STEPS};
        String selection = "UPPER(r_name) LIKE \"%" + name.toUpperCase() + "%\"";

        int search_id;
        String search_name;
        String search_ingr;
        String search_steps;

        Cursor cursor = myCR.query(ProviderContract.CONTENT_URI, projection, selection,
                null, null);

        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    search_id = cursor.getInt(cursor.getColumnIndex(COL_UID));
                    search_name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                    search_ingr = cursor.getString(cursor.getColumnIndex(COL_INGR));
                    search_steps = cursor.getString(cursor.getColumnIndex(COL_STEPS));

                    Recipe res = new Recipe(search_id, search_name, search_ingr, search_steps);
                    searchList.add(res);
                }while (cursor.moveToNext());
            }
        }

        return searchList;
    }

//    find recipe by id
    public Recipe queryRecipe (int id)
    {
        String[] projection = {COL_UID, COL_NAME, COL_INGR, COL_STEPS};
        String selection = "r_id = \"" + id + "\"";

        Cursor cursor = myCR.query(ProviderContract.CONTENT_URI, projection, selection, null, null);

        Recipe recipe = new Recipe();
        if(cursor.moveToFirst())
        {
            cursor.moveToFirst();
            recipe.setR_id(Integer.parseInt(cursor.getString(0)));
            recipe.setR_name(cursor.getString(1));
            recipe.setR_ingr(cursor.getString(2));
            recipe.setR_steps(cursor.getString(3));
            cursor.close();
        }else
            recipe = null;

        return recipe;
    }

    //    add recipe to db
    public void addRecipe(Recipe recipe)
    {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, recipe.getR_name());
        values.put(COL_INGR, recipe.getR_ingr());
        values.put(COL_STEPS, recipe.getR_steps());
        myCR.insert(ProviderContract.CONTENT_URI, values);
    }

    //    update existing recipe
    public void updateRecipe(Recipe recipe, int id)
    {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, recipe.getR_name());
        values.put(COL_INGR, recipe.getR_ingr());
        values.put(COL_STEPS, recipe.getR_steps());
        myCR.update(ProviderContract.CONTENT_URI, values, COL_UID + " = " + Integer.toString(id), null);
    }

    //    delete recipe from db by uid
    public boolean deleteRecipe(int ID)
    {
        boolean result = false;
        String selection = "r_id = \"" + Integer.toString(ID) + "\"";

        int rowsDeleted = myCR.delete(ProviderContract.CONTENT_URI,
                selection, null);

        if(rowsDeleted>0)
            result = true;

        return result;
    }
}
