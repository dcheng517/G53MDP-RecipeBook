package com.example.recipebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    public static final String log = "G53MDP";

    private RecipeDBHelper recipeDB;

    private EditText editName;
    private EditText editIngr;
    private EditText editSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeDB = new RecipeDBHelper(this, null, null, 1);

        editName = (EditText) findViewById(R.id.add_name);
        editIngr = (EditText) findViewById(R.id.add_ingr);
        editSteps = (EditText) findViewById(R.id.add_steps);


    }

//    submit button to add recipe
    public void onClickSubmit(View view)
    {
        if (editName.getText().toString().isEmpty())
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(AddActivity.this);
            alert
                    .setTitle("Invalid recipe name")
                    .setMessage("Recipe name cannot be blank")
                    .setCancelable(false)
                    .setPositiveButton("OK", null);

            alert.show();
        } else
        {
            Recipe newRecipe = new Recipe(editName.getText().toString(), editIngr.getText().toString(), editSteps.getText().toString());
            recipeDB.addRecipe(newRecipe);

            Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show();
            Log.d(log, "Add recipe");

            finish();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
