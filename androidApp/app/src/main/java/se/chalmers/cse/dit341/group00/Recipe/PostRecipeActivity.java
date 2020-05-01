package se.chalmers.cse.dit341.group00.Recipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import se.chalmers.cse.dit341.group00.R;
import se.chalmers.cse.dit341.group00.model.Ingredient;

public class PostRecipeActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText descriptionEditText;
    EditText nutInfoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recipe);

        this.nameEditText = findViewById(R.id.editText_recipeName);
        this.descriptionEditText = findViewById(R.id.editText_recipeDescription);
        this.nutInfoEditText = findViewById(R.id.editText_recipeNutInfo);

        this.nameEditText.setText("Tuna");
        this.descriptionEditText.setText("chunks");
        this.nutInfoEditText.setText("protein");
    }

    public void createRecipe(View view) {
        String recipeName = nameEditText.getText().toString();
        String recipeDescription = descriptionEditText.getText().toString();
        String recipeNutritionalInfo = nutInfoEditText.getText().toString();
        //Ingredient[]  ingredients =  new Ingredient[0];


        Map<String, String> params = new HashMap();

        String url = getString(R.string.server_url) + "/api/recipes";

        params.put("name", recipeName);
        params.put("description", recipeDescription);
        params.put("nutritionalInfo", recipeNutritionalInfo);
       // params.put( "ingredients", Ingredient[0]);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

        //This makes sure the post request goes through before going back to the main page.
        //It is a temporary solution for a bug and should be improved further.
        //if the post request happens after the start of the new activity,
        //the GET request of the new page doesn't refresh the ingredients, resulting in a bug (304 response)
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        }catch (Exception e) {
        }

        Intent intent = new Intent(this, RecipeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
