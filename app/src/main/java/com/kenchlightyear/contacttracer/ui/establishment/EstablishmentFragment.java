package com.kenchlightyear.contacttracer.ui.establishment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kenchlightyear.contacttracer.R;
import com.kenchlightyear.contacttracer.util.GpsTracker;

public class EstablishmentFragment extends Fragment {

    SharedPreferences sharedpreferences;
    TextView name;
    TextView lat;
    TextView lon;
    public static final String establishment = "establishment";
    public static final String Name = "name";
    double latitude = 14.5818;
    double longitude = 120.9770;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EstablishmentViewModel establishmentViewModel = ViewModelProviders.of(this).get(EstablishmentViewModel.class);
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
        String n = name.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, n);
        editor.apply();
    }

    public void Get(View view) {
        name = (TextView) view.findViewById(R.id.etName);
        lat = (TextView) view.findViewById(R.id.etLat);
        lon = (TextView) view.findViewById(R.id.etLong);
        sharedpreferences = this.getActivity().getSharedPreferences(establishment,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Name)) name.setText(sharedpreferences.getString(Name, ""));
        GpsTracker gpsTracker;
        gpsTracker = new GpsTracker(this.getActivity());
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }else{
            gpsTracker.showSettingsAlert();
        }
        lat.setText("Latitude: " + Double.toString(latitude));
        lon.setText("Longitude: " + Double.toString(longitude));
    }
}