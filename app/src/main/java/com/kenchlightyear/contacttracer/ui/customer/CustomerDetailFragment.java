package com.kenchlightyear.contacttracer.ui.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kenchlightyear.contacttracer.model.Customer;
import com.kenchlightyear.contacttracer.R;

public class CustomerDetailFragment extends Fragment {

    TextView name;
    TextView address;
    TextView barangay;
    TextView city;
    TextView province;
    TextView number;
    TextView email;

    View root;

    private CustomerDetailViewModel customerDetailViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        customerDetailViewModel =
                ViewModelProviders.of(this).get(CustomerDetailViewModel.class);
        root = inflater.inflate(R.layout.fragment_customer_detail, container, false);
        customerDetailViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Customer customer = getArguments().getParcelable("customer");
                Get(root, customer);
            }
        });
        return root;
    }

    public void Get(View view, Customer customer) {
        name = view.findViewById(R.id.etName);
        name.setText(customer.getFirst() + " " + customer.getLast());
        number = view.findViewById(R.id.etNumber);
        number.setText(customer.getNumber().toString());
        email = view.findViewById(R.id.etEmail);
        email.setText(customer.getEmail());
        address = view.findViewById(R.id.etAddress);
        address.setText(customer.getAddress());
        barangay = view.findViewById(R.id.etBarangay);
        barangay.setText(customer.getBarangay());
        city = view.findViewById(R.id.etCity);
        city.setText(customer.getCity());
        province = view.findViewById(R.id.etProvince);
        province.setText(customer.getProvince());
    }

}