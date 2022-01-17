package com.ybennun.parsedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //RequestQueue queue;
    String url = "https://www.google.com";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos";
    String getApiUrl = "https://jsonplaceholder.typicode.com/todos/1";
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //queue = Volley.newRequestQueue(this);
        queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        TextView textView = findViewById(R.id.textview);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getApiUrl, null,
                response -> {
                    try {
                        textView.setText(response.getString("title"));
                        Log.d("jsonObj", "onCreate: " + response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Log.d("", "onCreate: Failed! ");
        });

        queue.add(getJsonArrayRequest());
        //queue.add(jsonObjectRequest);
        //getJsonArrayRequest();


        //getString(queue);
    }

    @NonNull
    private JsonArrayRequest getJsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.d("JSON", "onCreate: " + jsonObject.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Log.d("JSON", "onCreate: Failed! "));
        return jsonArrayRequest;
    }

    private void getString(RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            //display the contents of our url
            Log.d("Main", "onCreate:" + response.substring(0, 500));
        }, error -> {
            Log.d("Main", "Failed to get info!");
        });
        //add the request to the RequestQueue
        queue.add(stringRequest);
    }
}