package com.example.recipebook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    public static final String log = "G53MDP";

    private EditText editName;
    private EditText editIngr;
    private EditText editSteps;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(log, "DetailsActivity onCreate");

         id = getIntent().getExtras().getInt("id");

        editName = (EditText) findViewById(R.id.edit_name);
        editIngr = (EditText) findViewById(R.id.edit_ingr);
        editSteps = (EditText) findViewById(R.id.edit_steps);

        getDetails();
    }

//    get details of recipe
    protected void getDetails()
    {
        Log.d(log, "getDetails()");

        RecipeDBHelper recipeDB = new RecipeDBHelper(this, null, null, 1);

        Recipe curr = recipeDB.queryRecipe(id);

        editName.setText(curr.getR_name());
        editIngr.setText(curr.getR_ingr());
        editSteps.setText(curr.getR_steps());

    }

//    submit button to edit recipe
    public void onClickSubmit(View view)
    {
        if (editName.getText().toString().isEmpty())
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);
            alert
                    .setTitle("Invalid recipe name")
                    .setMessage("Recipe name cannot be blank")
                    .setCancelable(false)
                    .setPositiveButton("OK", null);

            alert.show();
        } else
        {
            AlertDialog.Builder delAlert = new AlertDialog.Builder(this);
            delAlert
                    .setTitle("Confirm edit?")
                    .setMessage("Are you sure you want to edit this recipe?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RecipeDBHelper recipeDB = new RecipeDBHelper(EditActivity.this, null, null, 1);

                            Recipe editRecipe = new Recipe(editName.getText().toString(), editIngr.getText().toString(), editSteps.getText().toString());
                            recipeDB.updateRecipe(editRecipe, id);

                            Toast.makeText(EditActivity.this, "Recipe edited", Toast.LENGTH_SHORT).show();
                            Log.d(log, "Edit recipe");

                            finish();
                        }
                    })
                    .setNegativeButton("No", null);
            delAlert.show();

        }
    }

    @Override
    protected void onResume()
    {
        getDetails();
        super.onResume();
    }

}
