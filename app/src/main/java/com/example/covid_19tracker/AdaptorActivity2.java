package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptorActivity2 extends RecyclerView.Adapter<AdaptorActivity2.ViewHolder> {

    private List<CatItems> CatItems;
    private Context context;

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
        final CatItems CatItem = CatItems.get(position);
        if(CatItem.getCategory().contains("Testing")){
            holder.image.setImageResource(R.drawable.testing);
        }
        else if(CatItem.getCategory().contains("Food")){
            holder.image.setImageResource(R.drawable.freefood);
        }
        else{
            holder.image.setImageResource(R.drawable.hospital);
        }
        holder.nameoforg.setText(CatItem.getname());
        holder.description.setText(CatItem.getDescription());
        holder.phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone= CatItem.getPhoneNumber();
                Uri uri = Uri.parse("tel:" + phone.substring(0,10));
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                v.getContext().startActivity(intent);
            }
        });
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = CatItem.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CatItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameoforg,description;
        ImageView contact,phoneNumber,image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image_type);
            nameoforg=itemView.findViewById(R.id.nameOforg);
            description=itemView.findViewById(R.id.description);
            phoneNumber=itemView.findViewById(R.id.phoneNumber);
            contact=itemView.findViewById(R.id.location);
        }
    }

}


