package se.chalmers.cse.dit341.group00.User;

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
import se.chalmers.cse.dit341.group00.R;
import se.chalmers.cse.dit341.group00.model.User;

public class GetOneUserActivity extends AppCompatActivity{
    private TextView userView;
    private StringBuilder responseString = new StringBuilder();
    private String _id;
    private String name;
    private String password;


    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setUserView(View view) {
        this.userView = (TextView) view;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_user);

        Intent intent = getIntent();

        //setting ingredient view
        setUserView(findViewById(R.id.textView));

        //getting selected position from IngredientActivity
        String id = intent.getStringExtra(UserActivity.EXTRA_MESSAGE);

        set_id(id);
        requestUserById(id);
    }

    public void requestUserById(String id) {
        String url = getString(R.string.server_url) + "/api/users/" + id;

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
                            dataArray = response.getString("user");
                        } catch (JSONException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        User[] users = gson.fromJson(dataArray, User[].class);

                        set_id(users[0]._id);
                        setName(users[0].name);
                        setPassword(users[0].password);


                        resetResponseString();
                        appendResponseString("User ID: " + users[0]._id + "\n"
                                + "Name: " + users[0].name + "\n"
                                + "Password: " + users[0].password + "\n" );
                        setUserViewText(getResponseString().toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setUserViewText("User not found");
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void onClickDeleteOneUser(View view) {
        String url = getString(R.string.server_url) + "/api/users/" + this._id;

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

        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }


    public void onClickPutUser(View view) {
        Intent intent = new Intent(this, PutUserActivity.class);
        Bundle extras = createBundle();
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void onClickPatchUser(View view) {
        Intent intent = new Intent(this, PatchUserActivity.class);
        Bundle extras = createBundle();
        intent.putExtras(extras);
        startActivity(intent);
    }

    public Bundle createBundle() {
        Bundle extras = new Bundle();
        extras.putString("User_id", this._id);
        extras.putString("UserName", this.name);
        extras.putString("UserPassword", this.password);

        return extras;
    }

}
