package com.odev.sinemaprojesi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.odev.sinemaprojesi.Models.BudgetItem;
import com.odev.sinemaprojesi.R;
import com.odev.sinemaprojesi.TicketActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.odev.sinemaprojesi.LauncherActivity.state;

public class BudgetItemAdapter extends RecyclerView.Adapter<BudgetItemAdapter.define> {

    List<BudgetItem> list;
    Context context;

    public BudgetItemAdapter(Context context, List<BudgetItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BudgetItemAdapter.define onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.budgetadapter_layout,parent,false);

        return new BudgetItemAdapter.define(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetItemAdapter.define holder, int position) {
        holder.operation_tv.setText(list.get(position).getOperation());
        Log.i("buradanburadan",list.get(position).getOperation());
        holder.amount_tv.setText(list.get(position).getAmount() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class define extends RecyclerView.ViewHolder
    {
        TextView operation_tv, amount_tv;

        public define(View itemView){
            super(itemView);
            operation_tv = itemView.findViewById(R.id.operation_tv);
            amount_tv = itemView.findViewById(R.id.amount_tv);
        }
    }


}
