package se.chalmers.cse.dit341.group00.User;
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

public class PutUserActivity extends AppCompatActivity{

    private String _id;
    EditText nameEditText;
    EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_user);

        //get text editors by their IDs
        initializeTextEditors();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String id = extras.getString("User_id");
        String name = extras.getString("UserName");
        String password = extras.getString("UserPassword");


        //set text editors texts
        setTextEditorsText(name, password);

        //getting selected position from IngredientActivity. Either of the following works.
        this._id = id;
//        this._id = intent.getStringExtra(IngredientActivity.EXTRA_MESSAGE);
    }

    public void initializeTextEditors() {
        this.nameEditText = findViewById(R.id.editText_PutUserName);
        this.passwordEditText = findViewById(R.id.editText_PutUserPassword);

    }

    public void setTextEditorsText(String name, String password) {
        this.nameEditText.setText(name);
        this.passwordEditText.setText(password);

    }

    public void onClickPutUser(View view) {
        String url = getString(R.string.server_url) + "/api/users/" + this._id;

        //get what's on the text editors
        String userName = this.nameEditText.getText().toString();
        String userPassword = this.passwordEditText.getText().toString();


        Map<String, String> params = new HashMap();

        params.put("name", userName);
        params.put("password", userPassword);


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

        Intent intent = new Intent(this, UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
