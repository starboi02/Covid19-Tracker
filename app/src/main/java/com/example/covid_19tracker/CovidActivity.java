package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class CovidActivity extends AppCompatActivity {
    int j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_covid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Covid-19 Outbreak");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView t1 = (TextView) findViewById(R.id.link1);
        t1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t2 = (TextView) findViewById(R.id.link2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView t3 = (TextView) findViewById(R.id.link3);
        t3.setMovementMethod(LinkMovementMethod.getInstance());

    }
}