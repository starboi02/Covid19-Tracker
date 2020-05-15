package com.example.covid_19tracker;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.view.Menu;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GlobalActivity extends AppCompatActivity {

    private static final String URL_DATA="https://api.covid19api.com/summary";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems;
    private AdaptorActivity adaptorActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_global);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("World Data");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems=new ArrayList<>();

        loadRecyclerViewData();


    }
    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    String arrow="\u2191";
                    JSONObject jsonObject =new JSONObject(response);
                    JSONObject globalData= jsonObject.getJSONObject("Global");
                    String name ="World";
                    ListItems items = new ListItems(
                            name,
                            globalData.getString("TotalConfirmed"),
                            globalData.getString("TotalRecovered"),
                            globalData.getString("TotalDeaths"),
                            arrow+globalData.getString("NewConfirmed"),
                            arrow+globalData.getString("NewRecovered"),
                            arrow+globalData.getString("NewDeaths")
                    );
                    listItems.add(items);
                    JSONArray jsonArray = jsonObject.getJSONArray("Countries");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject country = jsonArray.getJSONObject(i);

                        ListItems item = new ListItems(
                                country.getString("Country"),
                                country.getString("TotalConfirmed"),
                                country.getString("TotalRecovered"),
                                country.getString("TotalDeaths"),arrow+ country.getString("NewConfirmed"),
                                arrow+country.getString("NewRecovered"),
                                arrow+country.getString("NewDeaths")
                        );
                        listItems.add(item);
                    }
                    adapter = new AdaptorActivity(listItems, getApplicationContext());
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

}
