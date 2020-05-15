package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class DistActivity extends AppCompatActivity {

    Spinner spinner;
    Spinner spinner_district;
    public static String URL = "https://api.covid19india.org/state_district_wise.json";
    ArrayList<String> StateName;
    ArrayList<String> DistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_dist);
        StateName = new ArrayList<>();
        DistName = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner_district = findViewById(R.id.spinner_district);

        loadSpinnerData(URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("District Data");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String state = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                DistName.clear();
                loadSpinnerDist(state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String dist = spinner_district.getItemAtPosition(spinner_district.getSelectedItemPosition()).toString();
                String state = spinner.getSelectedItem().toString();
                setData(dist,state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void setData(final String dist, final String state){
        TextView dist_name = findViewById(R.id.dist_name);
        final TextView dist_active = findViewById(R.id.dist_active);
        final TextView dist_recovered =findViewById(R.id.dist_recovered);
        final TextView dist_dead = findViewById(R.id.dist_dead);
        dist_name.setText(dist);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iter = jsonObject.keys();
                    while(iter.hasNext()){
                        String key = iter.next();
                        if(key.equals(state)){
                            JSONObject state = jsonObject.getJSONObject(key);
                            JSONObject distList = state.getJSONObject("districtData");
                            JSONObject district = distList.getJSONObject(dist);
                            JSONObject delta= district.getJSONObject("delta");

                            String c="\u2191";
                            dist_active.setText(district.getString("confirmed") +" (" + c + delta.getString("confirmed") +")");
                            dist_recovered.setText(district.getString("recovered") +" (" + c + delta.getString("recovered") +")");
                            dist_dead.setText(district.getString("deceased") +" (" + c + delta.getString("deceased") +")");

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iter = jsonObject.keys();
                    StateName.add("Select a State");
                    while(iter.hasNext()){
                        String key = iter.next();
                        StateName.add(key);
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(DistActivity.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerDist(final String state_name) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iter = jsonObject.keys();
                    DistName.add("Select a district");
                    while(iter.hasNext()){
                        String key = iter.next();

                        if(key.equals(state_name)){
                            JSONObject state = jsonObject.getJSONObject(key);
                            JSONObject distList = state.getJSONObject("districtData");
                            Iterator<String> it = distList.keys();

                            while (it.hasNext()){
                                String district = it.next();
                                DistName.add(district);
                            }
                        }
                    }
                    spinner_district.setAdapter(new ArrayAdapter<String>(DistActivity.this, android.R.layout.simple_spinner_dropdown_item, DistName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
