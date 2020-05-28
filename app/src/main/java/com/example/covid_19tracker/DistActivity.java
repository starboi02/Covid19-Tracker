package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DistActivity extends AppCompatActivity {

    Spinner spinner;
    Spinner spinner_district;
    public static String URL = "https://api.covid19india.org/state_district_wise.json";
    public static String URL_zon="https://api.covid19india.org/zones.json";
    public static String URL_res="https://api.covid19india.org/resources/resources.json";
    ArrayList<String> StateName;
    ArrayList<String> DistName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CatItems> catItems;


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
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        catItems=new ArrayList<>();
    }

    private void loadRecyclerViewData(final String dist, final String state){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_res, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONObject jsonObject =new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("resources");
                    catItems.clear();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject resource=jsonArray.getJSONObject(i);
                        if(resource.getString("city").equals(dist) || resource.getString("city").equals(state)){
                            String phoneNumber=resource.getString("phonenumber");
                            CatItems items =new CatItems(
                                    resource.getString("category"),
                                    resource.getString("nameoftheorganisation"),
                                    resource.getString("descriptionandorserviceprovided"),
                                    phoneNumber,
                                    resource.getString("contact")
                            );
                            catItems.add(items);
                        }
                    }
                    adapter = new AdaptorActivity2(catItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void setData(final String dist, final String state){
        final TextView dist_name = findViewById(R.id.district_info);
        final TextView dist_active = findViewById(R.id.inc_active);
        final TextView dist_recovered =findViewById(R.id.inc_recovered);
        final TextView dist_dead = findViewById(R.id.inc_deceased);

        RequestQueue requestQ=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringReq = new StringRequest(Request.Method.GET, URL_zon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("zones");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject district = jsonArray.getJSONObject(i);
                        if(district.getString("district").equals(dist)){
                            String temp = dist + " is a " + district.getString("zone") + " Zone";
                            dist_name.setText(temp);
                            dist_name.setTextColor(Color.WHITE);
                            if(district.getString("zone").equals("Green"))
                                dist_name.setBackgroundResource(R.drawable.green);
                            else if(district.getString("zone").equals("Red"))
                                dist_name.setBackgroundResource(R.drawable.red);
                            else
                                dist_name.setBackgroundResource(R.drawable.orange);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
                );
        int socketTimeout1 = 30000;
        RetryPolicy policy1 = new DefaultRetryPolicy(socketTimeout1, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringReq.setRetryPolicy(policy1);
        requestQ.add(stringReq);

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
        loadRecyclerViewData(dist,state);
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
