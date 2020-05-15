package com.example.covid_19tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private static final String URL_DATA="https://api.covid19india.org/state_district_wise.json";
    private static final String URL_INC="https://api.covid19india.org/data.json";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems;
    private Map<String,String> incCases= new HashMap<>();
    private Map<String,String> incRecovered= new HashMap<>();
    private Map<String,String> incDeceased= new HashMap<>();
    private Map<String,String> cases=new HashMap<>();
    private Map<String,String> recovered =new HashMap<>();
    private Map<String,String> deceased =new HashMap<>();
    private String natCases,natRecovered,natDeceased,total,totalRec,totalDec;
    private String arrow="\u2191";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("State Data");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listItems=new ArrayList<>();

        loadRecyclerViewData();

    }


    public void distData( View v){

        Intent intent = new Intent(MainActivity2.this, DistActivity.class);
        startActivity(intent);
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, URL_INC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("cases_time_series");
                    int current =jsonArray.length()-1;
                    JSONObject increment =jsonArray.getJSONObject(current);
                    natCases=increment.getString("dailyconfirmed");
                    natRecovered=increment.getString("dailyrecovered");
                    natDeceased=increment.getString("dailydeceased");
                    total=increment.getString("totalconfirmed");
                    totalRec=increment.getString("totalrecovered");
                    totalDec=increment.getString("totaldeceased");

                    JSONArray jsonArray1=jsonObject.getJSONArray("statewise");
                    for(int i=0;i<jsonArray1.length();i++){
                        JSONObject temp=jsonArray1.getJSONObject(i);
                        incCases.put(temp.getString("statecode"),temp.getString("deltaconfirmed"));
                        incRecovered.put(temp.getString("statecode"),temp.getString("deltarecovered"));
                        incDeceased.put(temp.getString("statecode"),temp.getString("deltadeaths"));
                        cases.put(temp.getString("statecode"),temp.getString("confirmed"));
                        recovered.put(temp.getString("statecode"),temp.getString("recovered"));
                        deceased.put(temp.getString("statecode"),temp.getString("deaths"));
                    }

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

        RequestQueue requestQ = Volley.newRequestQueue(this);
        requestQ.add(request);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iter = jsonObject.keys();

                    while(iter.hasNext()){
                        String key = iter.next();
                        JSONObject stateDetails = jsonObject.getJSONObject(key);
                        String code= stateDetails.getString("statecode");
                        ListItems items = new ListItems(
                                key,
                                cases.get(code),
                                recovered.get(code),
                                deceased.get(code),arrow+incCases.get(code),arrow+incRecovered.get(code),arrow+incDeceased.get(code)
                        );
                        listItems.add(items);

                    }
                    String str = "Total Cases";
                    ListItems items = new ListItems(
                            str,
                            total,
                            totalRec,totalDec,arrow+natCases,arrow+natRecovered,arrow+natDeceased
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