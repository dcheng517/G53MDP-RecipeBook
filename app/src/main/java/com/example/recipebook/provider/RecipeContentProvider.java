package com.example.recipebook.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.example.recipebook.ProviderContract;
import com.example.recipebook.RecipeDBHelper;

import static com.example.recipebook.RecipeDBHelper.log;


public class RecipeContentProvider extends ContentProvider {

    private RecipeDBHelper dbHelper;
    private SQLiteDatabase recipeDB;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int RECIPE = 1;
    public static final int RECIPE_ID = 2;

    static {
        uriMatcher.addURI(ProviderContract.AUTHORITY, RecipeDBHelper.TABLE, RECIPE);
        uriMatcher.addURI(ProviderContract.AUTHORITY, RecipeDBHelper.TABLE + "/#", RECIPE_ID);
    }

    @Override
    public boolean onCreate() {
        Log.d(log, "Content Provider onCreate.");
        dbHelper = new RecipeDBHelper(this.getContext(), null, null, 1);
        return false;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        recipeDB = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType)
        {
            case RECIPE:
                rowsDeleted = recipeDB.delete(RecipeDBHelper.TABLE, selection, selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();

                if(selection.isEmpty())
                    rowsDeleted = recipeDB.delete(RecipeDBHelper.TABLE,
                            RecipeDBHelper.COL_UID + " = " + id,
                            null);
                else
                    rowsDeleted = recipeDB.delete(RecipeDBHelper.TABLE,
                            RecipeDBHelper.COL_UID + " = " + id + " and " + selection,
                            selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

//    insert data to the db
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        recipeDB = dbHelper.getWritableDatabase();
        long id = 0;
        switch(uriType)
        {
            case RECIPE:
                id = recipeDB.insert(RecipeDBHelper.TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(ProviderContract.RECIPE_TABLE + "/" + id);
    }

//    query for recipe from db
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(RecipeDBHelper.TABLE);

        int uriType = uriMatcher.match(uri);
        recipeDB = dbHelper.getReadableDatabase();

        switch (uriType)
        {
            case RECIPE_ID:
                queryBuilder.appendWhere(RecipeDBHelper.COL_UID + "=" + uri.getLastPathSegment());
                break;
            case RECIPE:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        Cursor cursor = queryBuilder.query(recipeDB, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

//    update/edit of db
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = uriMatcher.match(uri);
        recipeDB = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType)
        {
            case RECIPE:
                rowsUpdated = recipeDB.update(RecipeDBHelper.TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();

                if(selection.isEmpty())
                    rowsUpdated = recipeDB.update(RecipeDBHelper.TABLE,
                            values,
                            RecipeDBHelper.COL_UID + " = " + id,
                            null);
                else
                    rowsUpdated = recipeDB.update(RecipeDBHelper.TABLE,
                            values,
                            RecipeDBHelper.COL_UID + " = " + id + " and " + selection,
                            selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(ProviderContract.BASE_URI, null);
        return rowsUpdated;
    }
}
