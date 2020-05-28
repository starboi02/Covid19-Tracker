package com.example.covid_19tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Tab2.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Tab2#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String URL="https://api.covid19india.org/data.json";
    private ArrayList<String> date;
    private Integer value=1000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private LineChart mChart;
    private BarChart barChart,barChart2,barChart3;

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onFragmentCreateSetTheme(getActivity());


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tab2, container, false);

        if (getArguments() != null) {
            value = getArguments().getInt("Theme",999);
        }
        date=new ArrayList<String>();


        mChart = (LineChart) view.findViewById(R.id.line_chart);
        mChart.setNoDataText("Loading...");
        mChart.setTouchEnabled(true);
        mChart.getDescription().setEnabled(false);

        barChart=view.findViewById(R.id.bar_chart);
        barChart.setDrawGridBackground(false);
        barChart.setNoDataText("Loading...");
        barChart.setNoDataTextColor(Color.WHITE);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.setAutoScaleMinMaxEnabled(true);
        YAxis yAxisa = barChart.getAxisRight();
        yAxisa.setAxisMinimum(0f);
        YAxis yAxisla=barChart.getAxisLeft();
        yAxisla.setAxisMinimum(0f);

        barChart2=view.findViewById(R.id.bar_chart2);
        barChart2.setDrawGridBackground(false);
        barChart2.setNoDataText("Loading...");
        barChart2.setNoDataTextColor(Color.WHITE);
        barChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart2.getDescription().setEnabled(false);
        barChart2.getAxisLeft().setEnabled(false);
        barChart2.getAxisLeft().setDrawGridLines(false);
        barChart2.getXAxis().setDrawGridLines(false);
        barChart2.getAxisRight().setDrawGridLines(false);
        barChart2.setAutoScaleMinMaxEnabled(true);
        YAxis yAxis = barChart2.getAxisRight();
        yAxis.setAxisMinimum(0f);
        YAxis yAxisl=barChart2.getAxisLeft();
        yAxisl.setAxisMinimum(0f);

        barChart3=view.findViewById(R.id.bar_chart3);
        barChart3.setDrawGridBackground(false);
        barChart3.setNoDataText("Loading...");
        barChart3.setNoDataTextColor(Color.WHITE);
        barChart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart3.getDescription().setEnabled(false);
        barChart3.getAxisLeft().setEnabled(false);
        barChart3.getAxisLeft().setDrawGridLines(false);
        barChart3.getXAxis().setDrawGridLines(false);
        barChart3.getAxisRight().setDrawGridLines(false);
        barChart3.setAutoScaleMinMaxEnabled(true);
        YAxis yAxisb = barChart3.getAxisRight();
        yAxisb.setAxisMinimum(0f);
        YAxis yAxisbl=barChart3.getAxisLeft();
        yAxisbl.setAxisMinimum(0f);


        extractData();
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        Legend ll = barChart.getLegend();
        ll.setForm(Legend.LegendForm.LINE);
        Legend lll = barChart2.getLegend();
        lll.setForm(Legend.LegendForm.LINE);
        Legend l3 = barChart3.getLegend();
        l3.setForm(Legend.LegendForm.LINE);

        mChart.getAxisLeft().setEnabled(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
//        mChart.getAxisRight().setTextColor(Color.WHITE);
//        mChart.getXAxis().setTextColor(Color.WHITE);
        YAxis yAxis1 =mChart.getAxisRight();
        yAxis1.setAxisMinimum(0f);
        YAxis yAxis2 =mChart.getAxisLeft();
        yAxis2.setAxisMinimum(0f);



        if(value%2==0){
            mChart.setNoDataTextColor(Color.BLACK);
            mChart.getAxisRight().setTextColor(Color.BLACK);
            mChart.getXAxis().setTextColor(Color.BLACK);
            l.setTextColor(Color.BLACK);
            ll.setTextColor(Color.BLACK);
            lll.setTextColor(Color.BLACK);
            l3.setTextColor(Color.BLACK);
            barChart.setNoDataTextColor(Color.BLACK);
            barChart.getAxisRight().setTextColor(Color.BLACK);
            barChart.getXAxis().setTextColor(Color.BLACK);

            barChart2.setNoDataTextColor(Color.BLACK);
            barChart2.getAxisRight().setTextColor(Color.BLACK);
            barChart2.getXAxis().setTextColor(Color.BLACK);

            barChart3.setNoDataTextColor(Color.BLACK);
            barChart3.getAxisRight().setTextColor(Color.BLACK);
            barChart3.getXAxis().setTextColor(Color.BLACK);

        }
        else{
            mChart.setNoDataTextColor(Color.WHITE);
            mChart.getAxisRight().setTextColor(Color.WHITE);
            mChart.getXAxis().setTextColor(Color.WHITE);
            l.setTextColor(Color.WHITE);
            ll.setTextColor(Color.WHITE);
            lll.setTextColor(Color.WHITE);
            l3.setTextColor(Color.WHITE);
            barChart.setNoDataTextColor(Color.WHITE);
            barChart.getAxisRight().setTextColor(Color.WHITE);
            barChart.getXAxis().setTextColor(Color.WHITE);

            barChart2.setNoDataTextColor(Color.WHITE);
            barChart2.getAxisRight().setTextColor(Color.WHITE);
            barChart2.getXAxis().setTextColor(Color.WHITE);

            barChart3.setNoDataTextColor(Color.WHITE);
            barChart3.getAxisRight().setTextColor(Color.WHITE);
            barChart3.getXAxis().setTextColor(Color.WHITE);
        }

        mChart.invalidate();
        barChart.invalidate();
        barChart2.invalidate();
        barChart3.invalidate();

        return view;
    }

    private void extractData(){

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("cases_time_series");
                    ArrayList<Entry> yVals=new ArrayList<>();
                    ArrayList<Entry> yVals1=new ArrayList<>();
                    ArrayList<Entry> yVals2=new ArrayList<>();
                    ArrayList<BarEntry> byVals=new ArrayList<>();
                    ArrayList<BarEntry> byVals1=new ArrayList<>();
                    ArrayList<BarEntry> byVals2=new ArrayList<>();
                    int j=-1;
                    for(int i=0;i<jsonArray.length();i++){
                        j++;
                        JSONObject currentDate =jsonArray.getJSONObject(i);
                        String x =currentDate.getString("date");
                        date.add(x.substring(0,6));
                        yVals.add(new Entry(j,currentDate.getInt("totalconfirmed")));
                        yVals1.add(new Entry(j,currentDate.getInt("totalrecovered")));
                        yVals2.add(new Entry(j,currentDate.getInt("totaldeceased")));
                        byVals.add(new BarEntry(j,currentDate.getInt("dailyconfirmed")));
                        byVals1.add(new BarEntry(j,currentDate.getInt("dailyrecovered")));
                        byVals2.add(new BarEntry(j,currentDate.getInt("dailydeceased")));
                    }
                    final ArrayList<String> xVals = date;

                    LineDataSet set1,set2,set3;
                    BarDataSet dataset = new BarDataSet(byVals,"DailyCases");
                    dataset.setColor(Color.rgb(255, 51, 51));
                    BarDataSet dataSet1=new BarDataSet(byVals1,"DailyRecovered");
                    dataSet1.setColor(Color.argb(90,0, 231, 0));
                    BarDataSet dataSet2=new BarDataSet(byVals2,"DailyDeceased");
                    dataSet2.setColor(Color.argb(80,50,50,225));


                    // create a dataset and give it a type
                    set1 = new LineDataSet(yVals, "Total Cases");
                    set2=new LineDataSet(yVals1,"Total Recovered");
                    set3=new LineDataSet(yVals2,"Total Death");
                    if(value%2!=0){
                        set1.setValueTextColor(Color.WHITE);
                        set2.setValueTextColor(Color.WHITE);
                        set3.setValueTextColor(Color.WHITE);
                        dataset.setValueTextColor(Color.WHITE);
                        dataSet1.setValueTextColor(Color.WHITE);
                        dataSet2.setValueTextColor(Color.WHITE);
                    }

                    set1.setColor(Color.RED);
                    set1.setLineWidth(1f);
                    set1.setDrawCircles(false);
                    set1.setValueTextSize(9f);

                    set2.setColor(Color.GREEN);
                    set2.setLineWidth(1f);
                    set2.setDrawCircles(false);
                    set2.setValueTextSize(9f);

                    set3.setColor(Color.BLUE);
                    set3.setLineWidth(1f);
                    set3.setDrawCircles(false);
                    set3.setValueTextSize(9f);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                    dataSets.add(set1);
                    dataSets.add(set2);
                    dataSets.add(set3);

                    ArrayList<IBarDataSet> barDataSets=new ArrayList<>();
                    barDataSets.add(dataset);

                    ArrayList<IBarDataSet> barDataSets1=new ArrayList<>();
                    barDataSets1.add(dataSet1);

                    ArrayList<IBarDataSet> barDataSets2=new ArrayList<>();
                    barDataSets2.add(dataSet2);

                    // create a data object with the datasets
                    LineData data = new LineData(dataSets);


                    BarData barData = new BarData(barDataSets);
                    BarData barData1 = new BarData(barDataSets1);
                    BarData barData2 = new BarData(barDataSets2);


                    XAxis xAxis = mChart.getXAxis();
                    xAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xVals.get((int) value);
                        }
                    });

                    XAxis bxAxis =barChart.getXAxis();
                    bxAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xVals.get((int) value);
                        }
                    });

                    XAxis bxAxis1 =barChart2.getXAxis();
                    bxAxis1.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xVals.get((int) value);
                        }
                    });

                    XAxis bxAxis2 =barChart3.getXAxis();
                    bxAxis2.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xVals.get((int) value);
                        }
                    });

                    mChart.setData(data);
                    IMarker marker = new MyMarkerView(getContext(),R.layout.custom_marker_view,xVals);
                    mChart.setMarker(marker);

                    barChart.setData(barData);
                    IMarker marker1 = new MyMarkerView(getContext(),R.layout.custom_marker_view,xVals);
                    barChart.setMarker(marker1);

                    barChart2.setData(barData1);
                    IMarker marker2 = new MyMarkerView(getContext(),R.layout.custom_marker_view,xVals);
                    barChart2.setMarker(marker2);

                    barChart3.setData(barData2);
                    IMarker marker3 = new MyMarkerView(getContext(),R.layout.custom_marker_view,xVals);
                    barChart3.setMarker(marker3);


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

        RequestQueue requestQ = Volley.newRequestQueue(getActivity());
        requestQ.add(request);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

