package com.example.covid_19tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    private String down="\u2193";



    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab1, container, false);
        Button btn=view.findViewById(R.id.btn_about);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distData();
            }
        });

        listItems=new ArrayList<>();
        show=new ArrayList<>();
        empty=new ArrayList<>();

        loadRecyclerViewData();
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;


    }

    public void distData(){

        Intent intent = new Intent(getActivity(), DistActivity.class);
        startActivity(intent);
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.Theme_MyDialog);
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
            RequestQueue requestQ = Volley.newRequestQueue(getActivity());
            requestQ.add(request);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iter = jsonObject.keys();
                    String name="Dadra and Nagar Haveli and Daman and Diu";
                    int z=1;
                    String str = "Total Cases";
                    int x=0,y=0;
                    if((total!=null && totalDec!=null) && totalRec!=null) {
                         x = Integer.parseInt(total) - Integer.parseInt(totalDec) - Integer.parseInt(totalRec);
                    }
                    if((natCases!=null&& natRecovered!=null)&& natDeceased!=null) {
                        y = Integer.parseInt(natCases) - Integer.parseInt(natRecovered) - Integer.parseInt(natDeceased);
                    }
                    String temp;
                    if(y<0)
                    temp=down+(y*-1);
                    else
                        temp=arrow+y;
                    ListItems item = new ListItems(
                            str,
                            total,
                            String.valueOf(x),
                            totalRec,totalDec,arrow+natCases,temp,arrow+natRecovered,arrow+natDeceased
                    );
                    listItems.add(item);
                    while(iter.hasNext()){
                        String key = iter.next();
                        JSONObject stateDetails = jsonObject.getJSONObject(key);
                        String code= stateDetails.getString("statecode");
                        if(key.equals(name)){
                            key="Dadra and Nagar Haveli";
                        }
                        int xx=0,yy=0;
                        if((cases.get(code)!=null&&recovered.get(code)!=null)&&deceased.get(code)!=null) {
                            xx = Integer.parseInt(cases.get(code)) - Integer.parseInt(recovered.get(code)) - Integer.parseInt(deceased.get(code));
                        }
                        if((incCases.get(code)!=null&& incRecovered!=null)&& incDeceased.get(code)!=null) {
                            yy = Integer.parseInt(incCases.get(code)) - Integer.parseInt(incRecovered.get(code)) - Integer.parseInt(incDeceased.get(code));
                        }
                        String o;
                        if(yy<0)
                            o=down+(yy*-1);
                        else
                            o=arrow+yy;
                        ListItems items = new ListItems(
                                key,
                                cases.get(code),
                                String.valueOf(xx),
                                recovered.get(code),
                                deceased.get(code),arrow+incCases.get(code),o,arrow+incRecovered.get(code),arrow+incDeceased.get(code)
                        );
                        hsh.put(key,z);
                        listItems.add(items);
                        z++;
                    }
                    adapter = new AdaptorActivity(listItems,getContext());
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
        if(getActivity()!=null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
