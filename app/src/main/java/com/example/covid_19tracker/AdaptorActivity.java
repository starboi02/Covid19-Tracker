package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptorActivity extends RecyclerView.Adapter<AdaptorActivity.ViewHolder> {

    private List<ListItems> listItems;
    private List<ListItems> searchList;
    private Context context;

    AdaptorActivity(List<ListItems> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItems listItem = listItems.get(position);
        holder.main_state.setText(listItem.getState());
        holder.main_active.setText(listItem.getActive());
        holder.main_new.setText(listItem.getNew());
        holder.main_recovered.setText(listItem.getRecovered());
        holder.main_deceased.setText(listItem.getDead());
        holder.inc_active.setText(listItem.getInc_active());
        holder.inc_new.setText(listItem.getInc_new());
        holder.inc_recovered.setText(listItem.getInc_recovered());
        holder.inc_deceased.setText(listItem.getInc_deceased());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView main_state;
        TextView main_active;
        TextView main_new;
        TextView main_recovered;
        TextView main_deceased;
        TextView inc_active;
        TextView inc_new;
        TextView inc_recovered;
        TextView inc_deceased;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            main_state = itemView.findViewById(R.id.main_state);
            main_active = itemView.findViewById(R.id.main_active);
            main_new=itemView.findViewById(R.id.main_new);
            main_recovered = itemView.findViewById(R.id.main_recovered);
            main_deceased = itemView.findViewById(R.id.main_deceased);
            inc_active = itemView.findViewById(R.id.inc_active);
            inc_new=itemView.findViewById(R.id.inc_new);
            inc_recovered = itemView.findViewById(R.id.inc_recovered);
            inc_deceased = itemView.findViewById(R.id.inc_deceased);
        }
    }

}


