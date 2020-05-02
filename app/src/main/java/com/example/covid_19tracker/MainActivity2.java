package com.example.covid_19tracker;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    static  int j=1;
    private RecyclerView mRecyclerView;

    private List<Object> mRecyclerViewItems = new ArrayList<>();


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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new RecyclerViewAdapter(this, mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        addMenuItemsFromJson();


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
//        if(item.getItemId()==R.id.about_covid){
//            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
//
//
//            startActivity(intent);
//        }
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

    private void addMenuItemsFromJson() {
        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);

                String State = menuItemObject.getString("state");
                String Active = menuItemObject.getString("active");
                String Deceased = menuItemObject.getString("deceased");
                String Recovered = menuItemObject.getString("recovered");

                Pojo pojo = new Pojo(State, Active, Deceased,
                        Recovered);
                mRecyclerViewItems.add(pojo);
            }
        } catch (IOException | JSONException exception) {
            Log.e(MainActivity.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.covid19);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

}