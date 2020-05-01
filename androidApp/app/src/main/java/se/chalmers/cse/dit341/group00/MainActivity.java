package se.chalmers.cse.dit341.group00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import se.chalmers.cse.dit341.group00.Ingredient.IngredientActivity;
import se.chalmers.cse.dit341.group00.Recipe.RecipeActivity;
import se.chalmers.cse.dit341.group00.User.UserActivity;


public class MainActivity extends AppCompatActivity {

    // Field for parameter name
    public static final String HTTP_PARAM = "httpResponse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewRecipe (View view) {
        //TextView mRecipeView = findViewById(R.id.recipeTextView);

        // Starts a new activity, providing the text from my HTTP text field as an input
        Intent intent = new Intent(this, RecipeActivity.class);
        //intent.putExtra(HTTP_PARAM, mRecipeView.getText().toString());
        startActivity(intent);
    }
    public void onClickNewUser (View view) {
       //TextView mUserView = findViewById(R.id.userTextView);

        // Starts a new activity, providing the text from my HTTP text field as an input
        Intent intent = new Intent(this, UserActivity.class);
        //intent.putExtra(HTTP_PARAM, mUserView.getText().toString());
        startActivity(intent);
    }
    public void onClickNewIngredient (View view) {
        //TextView mIngredientView = findViewById(R.id.ingredientTextView);

        // Starts a new activity, providing the text from my HTTP text field as an input
        Intent intent = new Intent(this, IngredientActivity.class);
        //intent.putExtra(HTTP_PARAM, mIngredientView.getText().toString());
        startActivity(intent);
    }



}
