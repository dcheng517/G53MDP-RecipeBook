package com.example.recipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String log = "G53MDP";

    private EditText searchText;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = (EditText) findViewById(R.id.searchText);

        Log.d(log, "MainActivity onCreate");

        setRecipeList();
    }

//    set listview with all recipes
    public void setRecipeList() {
        lv = (ListView) findViewById(R.id.recipeList);
        RecipeDBHelper recipeDB = new RecipeDBHelper(this, null, null, 1);

        Log.d(log, "setRecipeList()");
        List<Recipe> allRecipe = recipeDB.allRecipes();

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allRecipe));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt,
                                    long mylng) {
                Recipe selectedFromList = (Recipe) (lv.getItemAtPosition(myItemInt));
                Log.d(log, "Selected recipe with ID: " + Integer.toString(selectedFromList.getR_id()));

                Intent det = new Intent(getApplicationContext(), DetailsActivity.class);
                det.putExtra("id", selectedFromList.getR_id());
                startActivity(det);
            }
        });
    }

//    fills listview with searched recipes
    public void setRecipeList(String searchTxt) {
        lv = (ListView) findViewById(R.id.recipeList);
        RecipeDBHelper recipeDB = new RecipeDBHelper(this, null, null, 1);

        Log.d(log, "setRecipeList()");
        List<Recipe> searchList = recipeDB.queryRecipe(searchTxt);

        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt,
                                    long mylng) {
                Recipe selectedFromList = (Recipe) (lv.getItemAtPosition(myItemInt));
                Log.d(log, "Selected recipe with ID: " + Integer.toString(selectedFromList.getR_id()));

                Intent det = new Intent(getApplicationContext(), DetailsActivity.class);
                det.putExtra("id", selectedFromList.getR_id());
                startActivity(det);
            }
        });
    }

//    add new recipe
    public void onClickAdd(View view)
    {
        Intent add = new Intent(this, AddActivity.class);
        startActivity(add);
        Log.d(log, "onClickAdd");
    }

//    search for recipe
    public void onClickSearch(View view)
    {
        String searchTxt = searchText.getText().toString();
        setRecipeList(searchTxt);
    }

    @Override
    protected void onResume()
    {
        setRecipeList();
        super.onResume();
    }


}
