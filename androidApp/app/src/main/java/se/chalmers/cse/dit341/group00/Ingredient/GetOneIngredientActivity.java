package se.chalmers.cse.dit341.group00.Ingredient;

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
import se.chalmers.cse.dit341.group00.model.Ingredient;

public class GetOneIngredientActivity extends AppCompatActivity {

    private TextView ingredientView;
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

    public void setIngredientView(View view) {
        this.ingredientView = (TextView) view;
    }

    public void setIngredientViewText(String string) {
        this.ingredientView.setText(string);
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
        setContentView(R.layout.activity_individual_ingredient);

        Intent intent = getIntent();

        //setting ingredient view
        setIngredientView(findViewById(R.id.textView));

        //getting selected position from IngredientActivity
        String id = intent.getStringExtra(IngredientActivity.EXTRA_MESSAGE);

        set_id(id);
        requestIngredientById(id);
    }

    public void requestIngredientById(String id) {
        String url = getString(R.string.server_url) + "/api/ingredients/" + id;

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
                            dataArray = response.getString("ingredient");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        Ingredient[] ingredients = gson.fromJson(dataArray, Ingredient[].class);

                        set_id(ingredients[0]._id);
                        setName(ingredients[0].name);
                        setDescription(ingredients[0].description);
                        setNutritionalInfo(ingredients[0].nutritionalInfo);

                        resetResponseString();
                        appendResponseString("Ingredient ID: " + ingredients[0]._id + "\n"
                                + "Name: " + ingredients[0].name + "\n"
                                + "Description: " + ingredients[0].description + "\n"
                                + "Nutritional Info: " + ingredients[0].nutritionalInfo + "\n");
                        setIngredientViewText(getResponseString().toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setIngredientViewText("Ingredient not found");
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void onClickDeleteOneIngredient(View view) {
        String url = getString(R.string.server_url) + "/api/ingredients/" + this._id;

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

        Intent intent = new Intent(this, IngredientActivity.class);
        startActivity(intent);
    }


    public void onClickPutIngredient(View view) {
        Intent intent = new Intent(this, PutIngredientActivity.class);
        Bundle extras = createBundle();
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void onClickPatchIngredient(View view) {
        Intent intent = new Intent(this, PatchIngredientActivity.class);
        Bundle extras = createBundle();
        intent.putExtras(extras);
        startActivity(intent);
    }

    public Bundle createBundle() {
        Bundle extras = new Bundle();
        extras.putString("Ingredient_id", this._id);
        extras.putString("IngredientName", this.name);
        extras.putString("IngredientDescription", this.description);
        extras.putString("IngredientNutritionalInfo", this.nutritionalInfo);
        return extras;
    }
}
