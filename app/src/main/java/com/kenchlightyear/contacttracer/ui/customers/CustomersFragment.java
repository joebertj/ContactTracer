package com.kenchlightyear.contacttracer.ui.customers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kenchlightyear.contacttracer.R;

public class CustomersFragment extends Fragment {

    TextView timestamp;
    View root;

    private CustomersViewModel customersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customersViewModel =
                ViewModelProviders.of(this).get(CustomersViewModel.class);
        root = inflater.inflate(R.layout.fragment_customers, container, false);
        customersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Get(root);
            }
        });
        return root;
    }

    public void Get(View view) {
        timestamp = (TextView) view.findViewById(R.id.etTimestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String ts  = dateFormat.format(new Date());
        timestamp.setText(ts);
    }
}