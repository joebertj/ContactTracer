package com.kenchlightyear.contacttracer.ui.establishment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kenchlightyear.contacttracer.R;

public class EstablishmentFragment extends Fragment {

    private EstablishmentViewModel establishmentViewModel;
    SharedPreferences sharedpreferences;
    TextView name;
    public static final String establishment = "establishment";
    public static final String Name = "name";
    public static final String Latitude = "latitude";
    public static final String Longtitude = "longtitude";
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        establishmentViewModel =
                ViewModelProviders.of(this).get(EstablishmentViewModel.class);
        root = inflater.inflate(R.layout.fragment_establishment, container, false);
        Button button = (Button) root.findViewById(R.id.bSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        establishmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Get(root);
            }
        });
        return root;
    }

    public void Save() {
        Location location;
        double latitude = 0;
        double longitude = 0;

        String n = name.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, n);
        LocationManager lm = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
        editor.putFloat(Latitude, (float) latitude);
        editor.putFloat(Longtitude, (float) longitude);
        editor.commit();
    }

    public void Clear(View view) {
        name = (TextView) view.findViewById(R.id.etName);
        name.setText("");
    }

    public void Get(View view) {


        name = (TextView) view.findViewById(R.id.etName);
        sharedpreferences = this.getActivity().getSharedPreferences(establishment,
                Context.MODE_PRIVATE);


        if (sharedpreferences.contains(Name)) {
            name.setText(sharedpreferences.getString(Name, ""));
        }
    }

}