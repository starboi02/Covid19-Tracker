package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptorActivity extends RecyclerView.Adapter<AdaptorActivity.ViewHolder> {

    private List<ListItems> listItems;
    private Context context;

    public AdaptorActivity(List<ListItems> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItems listItem = listItems.get(position);
        holder.main_state.setText(listItem.getState());
        holder.main_active.setText(listItem.getActive());
        holder.main_deceased.setText(listItem.getDead());
        holder.main_recovered.setText(listItem.getRecovered());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView main_state;
        public TextView main_active;
        public TextView main_deceased;
        public TextView main_recovered;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_state = itemView.findViewById(R.id.main_state);
            main_active =itemView.findViewById(R.id.main_active);
            main_deceased =itemView.findViewById(R.id.main_deceased);
            main_recovered =itemView.findViewById(R.id.main_recovered);
        }
    }
}
