package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class DataActivity extends AppCompatActivity {

    CarouselView carouselView;
    LinearLayout linearLayout ;
    int[] sampleImages = {R.drawable.covid6,R.drawable.covid17,R.drawable.covid10,R.drawable.covid16,R.drawable.covid11};
    static  int j=1;
    private int k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_data);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Covid-19 Tracker");
        setSupportActionBar(toolbar);

//        linearLayout=findViewById(R.id.linearLayout);
//        linearLayout.setBackgroundResource(R.color.background);
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onStart(){
        super.onStart();
        k=0;
    }

    @Override
    public void onBackPressed() {
        k++;
        if(k == 1) {
            String message ="Click one more time to exist app";
            Toast toast= Toast.makeText(DataActivity.this,message, Toast.LENGTH_SHORT);
            View view = toast.getView();
            TextView text = view.findViewById(android.R.id.message);
            text.setBackgroundColor(16777215);
            toast.show();
        } else {
            Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
            homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
            homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeScreenIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.about_dev){
            Intent intent2 = new Intent(DataActivity.this, MainActivity4.class);
            startActivity(intent2);
        }
        else if( item.getItemId()== R.id.change_theme){
            if(j%2!=0) {
                themeUtils.changeToTheme(this, themeUtils.LIGHT);
//                linearLayout.setBackgroundResource(R.color.light);
                j++;
            }
            else {
                themeUtils.changeToTheme(this, themeUtils.DARK);
//                linearLayout.setBackgroundResource(R.color.background);
                j++;
            }
        }

        return super.onOptionsItemSelected(item);
    }
    public void aboutCovid19( View v){

        Intent intent = new Intent(DataActivity.this, MainActivity3.class);
        intent.putExtra("ThemeValue", j);
        startActivity(intent);
    }
    public void National( View v){

        Intent intent = new Intent(DataActivity.this, MainActivity2.class);
//        intent.putExtra("ThemeValue", j);
        startActivity(intent);
    }
    public void Global( View v){
        Intent intent = new Intent(DataActivity.this, GlobalActivity.class);
        startActivity(intent);
    }
    public void helpLine( View v){

        Uri uri = Uri.parse("tel:" + "01123978046"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }
    public void advisory(View v){
        Intent intent = new Intent(DataActivity.this, AdvisoryActivity.class);
        startActivity(intent);
    }
    public void faq( View v){
        Intent intent = new Intent(DataActivity.this, ActivityFaq.class);
        startActivity(intent);
    }
}