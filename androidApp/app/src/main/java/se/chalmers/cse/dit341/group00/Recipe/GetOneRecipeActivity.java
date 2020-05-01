package se.chalmers.cse.dit341.group00.Recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

import se.chalmers.cse.dit341.group00.R;
import se.chalmers.cse.dit341.group00.model.Recipe;

public class GetOneRecipeActivity extends AppCompatActivity{
    private TextView recipeView;
    private StringBuilder responseString = new StringBuilder();
    private String _id;
    private String name;
    private String description;
    private String nutritionalInfo;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNutritionalInfo(String nutritionalInfo) {
        this.nutritionalInfo = nutritionalInfo;
    }

    public void setRecipeView(View view) {
        this.recipeView = (TextView) view;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_recipe);

        Intent intent = getIntent();

        //setting recipe view
        setRecipeView(findViewById(R.id.textView));

        //getting selected position from IngredientActivity
        String id = intent.getStringExtra(RecipeActivity.EXTRA_MESSAGE);

        set_id(id);
        requestRecipeById(id);
    }

    public void requestRecipeById(String id) {
        String url = getString(R.string.server_url) + "/api/recipes/" + id;

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
                            dataArray = response.getString("recipe");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        Recipe[] recipes = gson.fromJson(dataArray, Recipe[].class);

                        set_id(recipes[0]._id);
                        setName(recipes[0].name);
                        setDescription(recipes[0].description);
                        setNutritionalInfo(recipes[0].nutritionalInfo);

                        resetResponseString();
                        appendResponseString("Recipe ID: " + recipes[0]._id + "\n"
                                + "Name: " + recipes[0].name + "\n"
                                + "Description: " + recipes[0].description + "\n"
                                + "Nutritional Info: " + recipes[0].nutritionalInfo + "\n");
                        setRecipeViewText(getResponseString().toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRecipeViewText("Recipe not found");
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void onClickDeleteOneRecipe(View view) {
        String url = getString(R.string.server_url) + "/api/recipes/" + this._id;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);

        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }


    public void onClickPutRecipe(View view) {
        Intent intent = new Intent(this, PutRecipeActivity.class);
        Bundle extras = createBundle();
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void onClickPatchRecipe(View view) {
        Intent intent = new Intent(this, PatchRecipeActivity.class);
        Bundle extras = createBundle();
        intent.putExtras(extras);
        startActivity(intent);
    }

    public Bundle createBundle() {
        Bundle extras = new Bundle();
        extras.putString("Recipe_id", this._id);
        extras.putString("RecipeName", this.name);
        extras.putString("RecipeDescription", this.description);
        extras.putString("RecipeNutritionalInfo", this.nutritionalInfo);
        return extras;
    }
}
