package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptorActivity2 extends RecyclerView.Adapter<AdaptorActivity2.ViewHolder> {

    private List<CatItems> CatItems;
    private Context context;
    private AdapterView.OnItemClickListener mlistner;

    public interface OnItemClickListner{
        void goToLink(String link);
    }
    public void setOnItemClickListner(AdapterView.OnItemClickListener listner){
        mlistner=listner;
    }
    AdaptorActivity2(List<CatItems> CatItems, Context context) {
        this.CatItems = CatItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_description, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CatItems CatItem = CatItems.get(position);
        holder.category.setText(CatItem.getCategory());
        holder.nameoforg.setText(CatItem.getname());
        holder.description.setText(CatItem.getDescription());
        holder.phoneNumber.setText(CatItem.getPhoneNumber());
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return CatItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView category,phoneNumber,nameoforg,description;
        Button contact;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.category);
            nameoforg=itemView.findViewById(R.id.nameOforg);
            description=itemView.findViewById(R.id.description);
            phoneNumber=itemView.findViewById(R.id.phoneNumber);
            contact=itemView.findViewById(R.id.location);
        }
    }

}


