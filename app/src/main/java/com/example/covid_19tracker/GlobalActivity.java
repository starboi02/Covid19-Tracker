package com.example.covid_19tracker;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.JsonReader;
import android.util.Log;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GlobalActivity extends AppCompatActivity {

    private static final String URL_DATA="https://api.covid19api.com/summary";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems,showList,empty;
    private Map<String,Integer> hsh=new HashMap<>();
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
        showList=new ArrayList<>();
        empty=new ArrayList<>();

        loadRecyclerViewData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionSearch);
        final androidx.appcompat.widget.SearchView searchView= (androidx.appcompat.widget.SearchView) searchViewItem.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        searchView.setQueryHint("Country");
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showList.clear();
                if(newText.length()==0){
                    adapter = new AdaptorActivity(listItems,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
                else {
                    Set set = hsh.entrySet();
                    Iterator itr = set.iterator();
                    int flag = 1;
                    while (itr.hasNext()) {
                        Map.Entry entry = (Map.Entry) itr.next();
                        String str = (String) entry.getKey();
                        if (str.length() >= newText.length()) {
                            if (str.substring(0, newText.length()).equals(newText)) {
                                showList.add(listItems.get((Integer) entry.getValue()));
                                flag = 0;
                            }
                        }
                    }
                    adapter = new AdaptorActivity(showList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    if (flag == 1) {
                        adapter = new AdaptorActivity(empty, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        String message ="No Country found";
                        Toast toast= Toast.makeText(GlobalActivity.this,message, Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView text = view.findViewById(android.R.id.message);
                        text.setBackgroundColor(16777215);
                        toast.show();
                    }
                }
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
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
                    hsh.put(name,0);
                    JSONArray jsonArray = jsonObject.getJSONArray("Countries");
                    String name1= "Iran, Islamic Republic of";
                    String name2="Macedonia, Republic of";
                    String temp;
                    int z=1;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject country = jsonArray.getJSONObject(i);
                        temp=country.getString("Country");
                        if(name1.equals(temp)){
                            temp="Iran";
                        }
                        if(name2.equals(temp)){
                            temp="Macedonia";
                        }
                        ListItems item = new ListItems(
                                temp,
                                country.getString("TotalConfirmed"),
                                country.getString("TotalRecovered"),
                                country.getString("TotalDeaths"),arrow+ country.getString("NewConfirmed"),
                                arrow+country.getString("NewRecovered"),
                                arrow+country.getString("NewDeaths")
                        );
                        listItems.add(item);
                        hsh.put(temp,z);
                        z++;
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
