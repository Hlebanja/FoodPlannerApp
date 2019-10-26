package se.chalmers.cse.dit341.group00.Ingredient;

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
import se.chalmers.cse.dit341.group00.model.Ingredient;

public class IngredientActivity extends AppCompatActivity {

    private TextView ingredientView;
    private StringBuilder responseString = new StringBuilder();
    private ArrayList<Ingredient> ingredientsList = new ArrayList<Ingredient>();

    //variable for the input text box
    private EditText mMessageEditText;
    //key for message
    public static final String EXTRA_MESSAGE =
            "ingredientMainActivityInput";

    //----------------- Auxiliary getters and setters for the class variables created -----------------
    // The following methods are necessary as they are used within the Volley Response Listener

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

    public void setIngredientsList(ArrayList list) {
        this.ingredientsList = list;
    }

    public void initializeIngredientsList() {
        String url = getString(R.string.server_url) + "/api/ingredients";
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
                            dataArray = response.getString("ingredients");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        Ingredient[] ingredients = gson.fromJson(dataArray, Ingredient[].class);

                        //creating the global
                        ArrayList<Ingredient> ingredientsList = new ArrayList<>(Arrays.asList(ingredients));
                        setIngredientsList(ingredientsList);

                        resetResponseString();
                        if (ingredientsList.size() == 0) {
                            appendResponseString("List is empty");
                        } else {
                            for (int i = 0; i < ingredients.length; i++) {
                                appendResponseString(i + ". " + ingredients[i].name
                                        + " - " + ingredients[i].description
                                        + " - " + ingredients[i].nutritionalInfo + "\n");
                            }
                        }

                        setIngredientViewText(getResponseString().toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setIngredientViewText("Error! " + error.toString());
                    }
                });

        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
    }

    //------------------------------ onCreate --------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.HTTP_PARAM);

        this.ingredientView = findViewById(R.id.ingredientsTextView);
        setIngredientViewText("Text from my main activity: " + message);

        mMessageEditText = findViewById(R.id.editText_main);
        initializeIngredientsList();
    }

    //------------------------------ API Methods --------------------------------------------------

    public void launchPostActivity(View view) {
        Intent intent = new Intent(this, PostIngredientActivity.class);
        startActivity(intent);
    }

    public void onClickDeleteIngredients(View view) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.server_url) + "/api/ingredients";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //textView.setText("Response is: "+ response.substring(0,500));
                        setIngredientViewText("Ingredients have been deleted!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIngredientViewText("That didn't work!");
            }
        });

//         Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void launchGetOneIngredientActivity(View view) {
        Intent intent = new Intent(this, GetOneIngredientActivity.class);

        //getting the ingredient _id
        String ingredientPosition = mMessageEditText.getText().toString();
        if (ingredientPosition.matches("") || !ingredientPosition.matches("[0-9]+")) {
//            setIngredientViewText("Invalid input");
        } else {
            int positionInt = Integer.parseInt(ingredientPosition);
            if (positionInt < 0 || positionInt >= this.ingredientsList.size()) {
//                setIngredientViewText("Out of bounds input");
            } else {
                Ingredient ingredient = this.ingredientsList.get(positionInt);
                String id = ingredient._id;
                intent.putExtra(EXTRA_MESSAGE, id);
                startActivity(intent);
            }
        }

        this.mMessageEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }
}

//    public void onClickGetIngredients(View view) {
//        String url = getString(R.string.server_url) + "/api/ingredients";
//        // Creates queue request
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // GSON allows to parse a JSON string/JSONObject directly into a user-defined class
//                        Gson gson = new Gson();
//
//                        String dataArray = null;
//
//                        try {
//                            dataArray = response.getString("ingredients");
//                        } catch (JSONException e) {
//                            Log.e(this.getClass().toString(), e.getMessage());
//                        }
//
//                        Ingredient[] ingredients = gson.fromJson(dataArray, Ingredient[].class);
//
//                        //creating the global
//                        ArrayList<Ingredient> ingredientsList = new ArrayList<>(Arrays.asList(ingredients));
//                        setIngredientsList(ingredientsList);
//
//                        resetResponseString();
//                        if (ingredientsList.size() == 0) {
//                            appendResponseString("List is empty");
//                        } else {
//                            for (int i = 0; i < ingredients.length; i++) {
//                                appendResponseString(i + ". " + ingredients[i].name
//                                        + " - " + ingredients[i].description
//                                        + " - " + ingredients[i].nutritionalInfo + "\n");
//                            }
//                        }
//                        setIngredientViewText(getResponseString().toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        setIngredientViewText("Error! " + error.toString());
//                    }
//                });
//
//        // The request queue makes sure that HTTP requests are processed in the right order.
//        queue.add(jsonObjectRequest);
//    }