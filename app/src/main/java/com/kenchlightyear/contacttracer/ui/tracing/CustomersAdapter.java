package com.kenchlightyear.contacttracer.ui.tracing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kenchlightyear.contacttracer.Customer;
import com.kenchlightyear.contacttracer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomersAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Customer> customers;

    public CustomersAdapter(List<Customer> customers) {
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
        Customer customer = customers.get(position);
        holder.getName().setText(customer.getFirst() + "  " + customer.getLast());
        holder.getNumber().setText(customer.getNumber().toString());
        holder.getEmail().setText(customer.getEmail());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

}