package com.example.covid_19tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
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
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    private static final String URL_DATA="https://api.covid19india.org/state_district_wise.json";
    private static final String URL_INC="https://api.covid19india.org/data.json";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItems> listItems,show,empty;
    private Map<String,String> incCases= new HashMap<>();
    private Map<String,String> incRecovered= new HashMap<>();
    private Map<String,String> incDeceased= new HashMap<>();
    private Map<String,String> cases=new HashMap<>();
    private Map<String,String> recovered =new HashMap<>();
    private Map<String,String> deceased =new HashMap<>();
    private Map<String,Integer> hsh = new HashMap<>();
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
        show=new ArrayList<>();
        empty=new ArrayList<>();

        loadRecyclerViewData();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu2, menu);
        MenuItem searchViewItem = menu.findItem(R.id.actionSearch);
        final SearchView searchView= (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("State");
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                show.clear();
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
                                show.add(listItems.get((Integer) entry.getValue()));
                                flag = 0;
                            }
                        }
                    }
                    adapter = new AdaptorActivity(show, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    if (flag == 1) {
                        adapter = new AdaptorActivity(empty, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        String message ="No state found";
                        Toast toast= Toast.makeText(MainActivity2.this,message, Toast.LENGTH_SHORT);
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
                    String name="Dadra and Nagar Haveli and Daman and Diu";
                    int z=1;
                    String str = "Total Cases";
                    ListItems item = new ListItems(
                            str,
                            total,
                            totalRec,totalDec,arrow+natCases,arrow+natRecovered,arrow+natDeceased
                    );
                    listItems.add(item);
                    while(iter.hasNext()){
                        String key = iter.next();
                        JSONObject stateDetails = jsonObject.getJSONObject(key);
                        String code= stateDetails.getString("statecode");
                        if(key.equals(name)){
                            key="Dadra and Nagar Haveli";
                        }
                        ListItems items = new ListItems(
                                key,
                                cases.get(code),
                                recovered.get(code),
                                deceased.get(code),arrow+incCases.get(code),arrow+incRecovered.get(code),arrow+incDeceased.get(code)
                        );
                        hsh.put(key,z);
                        listItems.add(items);
                        z++;
                    }
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