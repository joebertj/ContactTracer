package com.kenchlightyear.contacttracer.ui.customers;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kenchlightyear.contacttracer.model.Customer;
import com.kenchlightyear.contacttracer.MainActivity;
import com.kenchlightyear.contacttracer.R;
import com.kenchlightyear.contacttracer.ui.customer.CustomerDetailFragment;

import java.util.List;

public class CustomersAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Customer> customers;

    ViewGroup vg;

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
        vg = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
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
                switchContent(customer);
            }
        };
        holder.getName().setOnClickListener(listener);
        holder.getNumber().setOnClickListener(listener);
        holder.getEmail().setOnClickListener(listener);
    }

    public void switchContent(Customer customer) {
        Context cContext = vg.getContext();
        Log.d("CONTEXT", new Boolean(cContext instanceof MainActivity).toString());
        if (cContext == null)
            return;
        if (cContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) cContext;
            FragmentContainerView view = (FragmentContainerView) mainActivity.findViewById(R.id.nav_host_fragment);
            view.removeAllViews();
            FragmentManager sfm = mainActivity.getSupportFragmentManager();
            Fragment fragment;
            fragment = sfm.findFragmentByTag("CUSTOMER");
            if(fragment==null)
                fragment = new CustomerDetailFragment();
            Bundle mBundle = new Bundle();
            mBundle.putParcelable("customer", customer);
            fragment.setArguments(mBundle);
            FragmentTransaction ft = sfm.beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment, "CUSTOMER");
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

}