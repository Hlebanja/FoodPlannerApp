package se.chalmers.cse.dit341.group00.Ingredient;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import se.chalmers.cse.dit341.group00.R;

public class PostIngredientActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText descriptionEditText;
    EditText nutInfoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ingredient);

        this.nameEditText = findViewById(R.id.editText_ingredientName);
        this.descriptionEditText = findViewById(R.id.editText_ingredientDescription);
        this.nutInfoEditText = findViewById(R.id.editText_ingredientNutInfo);

        this.nameEditText.setText("Tuna");
        this.descriptionEditText.setText("chunks");
        this.nutInfoEditText.setText("protein");
    }

    public void createIngredient(View view) {
        String ingredientName = nameEditText.getText().toString();
        String ingredienDescription = descriptionEditText.getText().toString();
        String ingredientNutritionalInfo = nutInfoEditText.getText().toString();

        Map<String, String> params = new HashMap();

        String url = getString(R.string.server_url) + "/api/ingredients";

        params.put("name", ingredientName);
        params.put("description", ingredienDescription);
        params.put("nutritionalInfo", ingredientNutritionalInfo);

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

        Intent intent = new Intent(this, IngredientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
