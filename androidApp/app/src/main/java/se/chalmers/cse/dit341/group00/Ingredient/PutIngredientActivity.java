package se.chalmers.cse.dit341.group00.Ingredient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import se.chalmers.cse.dit341.group00.R;
import se.chalmers.cse.dit341.group00.model.Ingredient;

public class PutIngredientActivity extends AppCompatActivity {

    private String _id;
    EditText nameEditText;
    EditText descriptionEditText;
    EditText nutInfoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_ingredient);

        //get text editors by their IDs
        initializeTextEditors();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String id = extras.getString("Ingredient_id");
        String name = extras.getString("IngredientName");
        String description = extras.getString("IngredientDescription");
        String nutritionalInfo = extras.getString("IngredientNutritionalInfo");

        //set text editors texts
        setTextEditorsText(name, description, nutritionalInfo);

        //getting selected position from IngredientActivity. Either of the following works.
        this._id = id;
//        this._id = intent.getStringExtra(IngredientActivity.EXTRA_MESSAGE);
    }

    public void initializeTextEditors() {
        this.nameEditText = findViewById(R.id.editText_PutIngredientName);
        this.descriptionEditText = findViewById(R.id.editText_PutIngredientDescription);
        this.nutInfoEditText = findViewById(R.id.editText_PutIngredientNutInfo);
    }

    public void setTextEditorsText(String name, String description, String nutInfo) {
        this.nameEditText.setText(name);
        this.descriptionEditText.setText(description);
        this.nutInfoEditText.setText(nutInfo);
    }

    public void onClickPutIngredient(View view) {
        String url = getString(R.string.server_url) + "/api/ingredients/" + this._id;

        //get what's on the text editors
        String ingredientName = this.nameEditText.getText().toString();
        String ingredientDescription = this.descriptionEditText.getText().toString();
        String ingredientNutritionalInfo = this.nutInfoEditText.getText().toString();

        Map<String, String> params = new HashMap();

        params.put("name", ingredientName);
        params.put("description", ingredientDescription);
        params.put("nutritionalInfo", ingredientNutritionalInfo);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle response
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(jsonRequest);

        Intent intent = new Intent(this, IngredientActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}