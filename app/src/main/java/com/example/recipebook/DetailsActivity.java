package com.example.recipebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    public static final String log = "G53MDP";

    private TextView r_name;
    private TextView r_ingr;
    private TextView r_steps;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(log, "DetailsActivity onCreate");

        id = getIntent().getExtras().getInt("id");

        r_name = (TextView) findViewById(R.id.det_name);
        r_ingr = (TextView) findViewById(R.id.det_ingr);
        r_steps = (TextView) findViewById(R.id.det_steps);

        getDetails();
    }

//    fills textview with recipe details
    protected void getDetails()
    {
        Log.d(log, "getDetails()");

        RecipeDBHelper recipeDB = new RecipeDBHelper(this, null, null, 1);

        Recipe curr = recipeDB.queryRecipe(id);

        r_name.setText(curr.getR_name());
        r_ingr.setText(curr.getR_ingr());
        r_steps.setText(curr.getR_steps());

    }

//    to edit recipe
    public void onClickEdit(View view)
    {
        Intent edit = new Intent(this, EditActivity.class);
        edit.putExtra("id", id);
        startActivity(edit);
    }

//    to delete recipe
    public void onClickDelete(View view)
    {
        AlertDialog.Builder delAlert = new AlertDialog.Builder(this);
        delAlert
                .setTitle("Confirm deletion?")
                .setMessage("Deleted recipes will not be recoverable." +
                        "Are you sure you want to delete this recipe?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RecipeDBHelper recipeDB = new RecipeDBHelper(DetailsActivity.this, null, null, 1);
                        recipeDB.deleteRecipe(id);
                        Log.d(log, "deleteRecipe: " + id);
                        Toast.makeText(DetailsActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", null);
        delAlert.show();
    }

    @Override
    protected void onResume()
    {
        getDetails();
        super.onResume();
    }

}
