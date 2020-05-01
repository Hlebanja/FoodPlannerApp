package se.chalmers.cse.dit341.group00.Recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import se.chalmers.cse.dit341.group00.MainActivity;
import se.chalmers.cse.dit341.group00.R;
import se.chalmers.cse.dit341.group00.model.Recipe;

public class RecipeActivity extends AppCompatActivity {

    private TextView recipeView;
    private StringBuilder responseString = new StringBuilder();
    private ArrayList<Recipe> recipesList = new ArrayList<Recipe>();

    //variable for the input text box
    private EditText mMessageEditText;
    //key for message
    public static final String EXTRA_MESSAGE =
            "recipeMainActivityInput";

    //----------------- Auxiliary getters and setters for the class variables created -----------------
    // The following methods are necessary as they are used within the Volley Response Listener

    public void setRecipeViewText(String string) {
        this.recipeView.setText(string);
    }

    public void resetResponseString() {
        this.responseString.setLength(0);
    }

    public void appendResponseString(String string) {
        this.responseString.append(string);
    }

    public StringBuilder getResponseString() {
        return this.responseString;
    }

    public void setRecipesList(ArrayList list) {
        this.recipesList = list;
    }

    public void initializeRecipesList() {
        String url = getString(R.string.server_url) + "/api/recipes";
        // Creates queue request
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
                        Gson gson = new Gson();

                        String dataArray = null;

                        try {
                            dataArray = response.getString("recipes");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        Recipe[] recipes = gson.fromJson(dataArray, Recipe[].class);

                        //creating the global
                        ArrayList<Recipe> recipesList = new ArrayList<>(Arrays.asList(recipes));
                        setRecipesList(recipesList);

                        resetResponseString();
                        if (recipesList.size() == 0) {
                            appendResponseString("List is empty");
                        } else {
                            for (int i = 0; i < recipes.length; i++) {
                                appendResponseString(i + ". " + recipes[i].name
                                        + " - " + recipes[i].description
                                        + " - " + recipes[i].nutritionalInfo + "\n");
                            }
                        }

                        setRecipeViewText(getResponseString().toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRecipeViewText("Error! " + error.toString());
                    }
                });

        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
    }

    //------------------------------ onCreate --------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.HTTP_PARAM);

        this.recipeView = findViewById(R.id.recipesTextView);
        setRecipeViewText("Text from my main activity: " + message);

        mMessageEditText = findViewById(R.id.editText_main);
        initializeRecipesList();
    }

    //------------------------------ API Methods --------------------------------------------------

    public void launchPostActivity(View view) {
        Intent intent = new Intent(this, PostRecipeActivity.class);
        startActivity(intent);
    }

    public void onClickDeleteRecipes(View view) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.server_url) + "/api/recipes";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //textView.setText("Response is: "+ response.substring(0,500));
                        setRecipeViewText("Recipes have been deleted!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setRecipeViewText("That didn't work!");
            }
        });

//         Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void launchGetOneRecipeActivity(View view) {
        Intent intent = new Intent(this, GetOneRecipeActivity.class);

        //getting the ingredient _id
        String recipePosition = mMessageEditText.getText().toString();
        if (recipePosition.matches("") || !recipePosition.matches("[0-9]+")) {
//            setIngredientViewText("Invalid input");
        } else {
            int positionInt = Integer.parseInt(recipePosition);
            if (positionInt < 0 || positionInt >= this.recipesList.size()) {
//                setIngredientViewText("Out of bounds input");
            } else {
                Recipe recipe = this.recipesList.get(positionInt);
                String id = recipe._id;
                intent.putExtra(EXTRA_MESSAGE, id);
                startActivity(intent);
            }
        }

        this.mMessageEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }
}
