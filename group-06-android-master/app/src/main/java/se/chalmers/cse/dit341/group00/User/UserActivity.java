package se.chalmers.cse.dit341.group00.User;


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
import se.chalmers.cse.dit341.group00.model.User;

public class UserActivity extends AppCompatActivity {
    private TextView userView;
    private StringBuilder responseString = new StringBuilder();
    private ArrayList<User> usersList = new ArrayList<User>();

    //variable for the input text box
    private EditText mMessageEditText;
    //key for message
    public static final String EXTRA_MESSAGE =
            "userMainActivityInput";

    //----------------- Auxiliary getters and setters for the class variables created -----------------
    // The following methods are necessary as they are used within the Volley Response Listener

    public void setUserViewText(String string) {
        this.userView.setText(string);
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

    public void setUsersList(ArrayList list) {
        this.usersList = list;
    }

    public void initializeUsersList() {
        String url = getString(R.string.server_url) + "/api/users";
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
                            dataArray = response.getString("users");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        User[] users = gson.fromJson(dataArray, User[].class);

                        //creating the global
                        ArrayList<User> usersList = new ArrayList<>(Arrays.asList(users));
                        setUsersList(usersList);

                        resetResponseString();
                        if (usersList.size() == 0) {
                            appendResponseString("List is empty");
                        } else {
                            for (int i = 0; i < users.length; i++) {
                                appendResponseString(i + ". " + users[i].name
                                        + " - " + users[i].password
                                         + "\n");
                            }
                        }

                        setUserViewText(getResponseString().toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setUserViewText("Error! " + error.toString());
                    }
                });

        // The request queue makes sure that HTTP requests are processed in the right order.
        queue.add(jsonObjectRequest);
    }

    //------------------------------ onCreate --------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.HTTP_PARAM);

        this.userView = findViewById(R.id.usersTextView);
        setUserViewText("Text from my main activity: " + message);

        mMessageEditText = findViewById(R.id.editText_main);
        initializeUsersList();
    }

    //------------------------------ API Methods --------------------------------------------------

    public void launchPostActivity(View view) {
        Intent intent = new Intent(this, PostUserActivity.class);
        startActivity(intent);
    }

    public void onClickDeleteUsers(View view) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.server_url) + "/api/users";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //textView.setText("Response is: "+ response.substring(0,500));
                        setUserViewText("Users have been deleted!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setUserViewText("That didn't work!");
            }
        });

//         Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void launchGetOneUserActivity(View view) {
        Intent intent = new Intent(this, GetOneUserActivity.class);

        //getting the ingredient _id
        String userPosition = mMessageEditText.getText().toString();
        if (userPosition.matches("") || !userPosition.matches("[0-9]+")) {
//            setIngredientViewText("Invalid input");
        } else {
            int positionInt = Integer.parseInt(userPosition);
            if (positionInt < 0 || positionInt >= this.usersList.size()) {
//                setIngredientViewText("Out of bounds input");
            } else {
                User user = this.usersList.get(positionInt);
                String id = user._id;
                intent.putExtra(EXTRA_MESSAGE, id);
                startActivity(intent);
            }
        }

        this.mMessageEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }
}
