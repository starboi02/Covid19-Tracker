package com.example.covid_19tracker;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class MyMarkerView extends MarkerView {
    private TextView tvContent;
    ArrayList<String> mXLabels;
    public MyMarkerView(Context context, int layoutResource, ArrayList<String> xLabels) {
        super(context, layoutResource);
        // find your layout components
        tvContent = (TextView) findViewById(R.id.tvContent);
        mXLabels = xLabels;
    }
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String xVal= mXLabels.get((int)e.getX());
        tvContent.setText(xVal + "\n" + (int)e.getY());
        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }
    private MPPointF mOffset;
    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }
}
