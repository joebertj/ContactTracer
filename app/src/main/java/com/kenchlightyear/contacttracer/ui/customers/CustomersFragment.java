package com.kenchlightyear.contacttracer.ui.customers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kenchlightyear.contacttracer.R;

public class CustomersFragment extends Fragment {

    private CustomersViewModel customersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customersViewModel =
                ViewModelProviders.of(this).get(CustomersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        customersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ;
            }
        });
        return root;
    }
}