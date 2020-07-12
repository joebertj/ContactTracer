package com.kenchlightyear.contacttracer.ui.tracing;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kenchlightyear.contacttracer.Customer;
import com.kenchlightyear.contacttracer.MainActivity;
import com.kenchlightyear.contacttracer.R;
import com.kenchlightyear.contacttracer.ui.CustomerDetailFragment;

import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Customer> customers;

    Context cContext;

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
        cContext = parent.getContext();
        View view = LayoutInflater.from(cContext).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final Customer customer = customers.get(position);
        holder.getName().setText(customer.getFirst() + "  " + customer.getLast());
        holder.getNumber().setText(customer.getNumber().toString());
        holder.getEmail().setText(customer.getEmail());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CUSTOMER", customer.toString());
                fragmentJump(customer);
            }
        };
        holder.getName().setOnClickListener(listener);
        holder.getNumber().setOnClickListener(listener);
        holder.getEmail().setOnClickListener(listener);
    }

    private void fragmentJump(Customer customer) {
        Fragment mFragment = new CustomerDetailFragment();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("customer", customer);
        mFragment.setArguments(mBundle);
        switchContent(mFragment);
    }

    public void switchContent(Fragment fragment) {
        Log.d("CONTEXT", new Boolean(cContext instanceof MainActivity).toString());
        if (cContext == null)
            return;
        if (cContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) cContext;
            mainActivity.switchContent(fragment);
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

}