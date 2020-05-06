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
import android.widget.Button;
import android.view.Menu;

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

public class MainActivity2 extends AppCompatActivity {

    static  int j=1;
    private static final String URL_DATA="https://covid19sggts04.herokuapp.com/";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("Covid-19 Tracker");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems=new ArrayList<>();

        loadRecyclerViewData();


    }

    public void aboutCovid19( View v){

        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.about_dev){
            Intent intent2 = new Intent(MainActivity2.this, MainActivity4.class);

            startActivity(intent2);
        }
        else if( item.getItemId()== R.id.change_theme){
                if(j%2!=0) {
                    themeUtils.changeToTheme(this, themeUtils.LIGHT);
                    j++;
                }
                else {
                    themeUtils.changeToTheme(this, themeUtils.DARK);
                    j++;
                }
        }
        return super.onOptionsItemSelected(item);
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
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject stateData = jsonObject.getJSONObject("stateData");
                    JSONObject nationData = jsonObject.getJSONObject("countryData");
                    Iterator<String> iter = stateData.keys();
                    while(iter.hasNext()){
                        String key = iter.next();
                        JSONObject stateDetails = stateData.getJSONObject(key);


                        ListItems items = new ListItems(
                                key,
                                stateDetails.getString("cases"),
                                stateDetails.getString("cured_discharged"),
                                stateDetails.getString("deaths")
                        );
                        listItems.add(items);
                    }
                    String str = "Total Cases";
                    ListItems items = new ListItems(
                            str,
                            nationData.getString("total"),
                            nationData.getString("deathsTotal"),
                            nationData.getString("cured_dischargedTotal")
                            );
                    listItems.add(items);
                    adapter = new AdaptorActivity(listItems,getApplicationContext());
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