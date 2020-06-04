package com.example.covid_19tracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptorActivityResources extends RecyclerView.Adapter<AdaptorActivityResources.ViewHolder> {

    private List<CategoryItems> CategoryItems;
    private Context context;

    AdaptorActivityResources(List<CategoryItems> CategoryItems, Context context) {
        this.CategoryItems = CategoryItems;
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
        final CategoryItems CatItem = CategoryItems.get(position);
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
        return CategoryItems.size();
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


