package com.kenchlightyear.contacttracer.ui.tracing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kenchlightyear.contacttracer.R;

import java.util.Random;

public class CustomersAdapater extends RecyclerView.Adapter<RecyclerViewHolder> {
    private String [] customers;

    public CustomersAdapater(String [] customers) {
        this.customers = customers;
}

    @Override
    public int getItemViewType(final int position) {
        return R.layout.frame_textview;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getView().setText(customers[position]);
    }

    @Override
    public int getItemCount() {
        return customers.length;
    }
}
