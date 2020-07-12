package com.kenchlightyear.contacttracer.ui.customers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.kenchlightyear.contacttracer.ui.tracing.CustomersAdapater;
import com.kenchlightyear.contacttracer.ui.tracing.TracingFragment;
import com.kenchlightyear.contacttracer.util.GpsTracker;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;

public class CustomersFragment extends Fragment {

    SharedPreferences sharedpreferences;

    String first;
    String last;
    String address;
    String barangay;
    String city;
    String province;
    String n;
    Long number;
    String email;
    String t;
    Float temperature;
    TextView timestamp;
    TextView feedback;
    String ts;
    String name;
    String establishmentId;
    String lat;
    String lon;

    public static final String establishment = "establishment";
    public static final String Name = "name";
    public static final String UniqueID = "uuid";

    double latitude = 14.5818;
    double longitude = 120.9770;

    View root;

    private CustomersViewModel customersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customersViewModel =
                ViewModelProviders.of(this).get(CustomersViewModel.class);
        root = inflater.inflate(R.layout.fragment_customers, container, false);
        Button button = (Button) root.findViewById(R.id.bSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(root);
            }
        });
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
        ts  = dateFormat.format(new Date());
        timestamp.setText(ts);
    }

    public void Save(View view){
        feedback = (TextView) view.findViewById(R.id.etFeedback);
        feedback.setVisibility(View.INVISIBLE);
        feedback.setTextColor(RED);
        first = ((TextView) view.findViewById(R.id.etFirst)).getText().toString();
        last = ((TextView) view.findViewById(R.id.etLast)).getText().toString();
        address = ((TextView) view.findViewById(R.id.etAddress)).getText().toString();
        barangay = ((TextView) view.findViewById(R.id.etBarangay)).getText().toString();
        city = ((TextView) view.findViewById(R.id.etCity)).getText().toString();
        province = ((TextView) view.findViewById(R.id.etProvince)).getText().toString();
        n = ((TextView) view.findViewById(R.id.etNumber)).getText().toString();
        email = ((TextView) view.findViewById(R.id.etEmail)).getText().toString();
        t = ((TextView) view.findViewById(R.id.etTemperature)).getText().toString();
        boolean validated = true;
        if(first == null || last == null || address == null || barangay == null || city == null
                || province == null || n == null || email == null || t == null
                || first.isEmpty() || last.isEmpty() || address.isEmpty() || barangay.isEmpty() || city.isEmpty()
                || province.isEmpty() || n.isEmpty() || email.isEmpty() || t.isEmpty() ) {
            feedback.setText("All Fields are required");
            feedback.setVisibility(View.VISIBLE);
            validated = false;
        } else {
            number = Long.valueOf(n);
            temperature = Float.valueOf(t);
            if((number > 99999999 && number < 9000000000L) || number > 9999999999L || number < 1000000) {
                feedback.setText("Contact number has extra or missing digits. 7-8 digits landline and 10-11 digits cellular are accepted");
                feedback.setVisibility(View.VISIBLE);
                validated = false;
            }
            if(temperature < 35 || temperature > 41 ) {
                feedback.setText("Temperature is betweeen 35 and 41");
                feedback.setVisibility(View.VISIBLE);
                validated = false;
            }
        }
        if(validated) {
            sharedpreferences = this.getActivity().getSharedPreferences(establishment,
                    Context.MODE_PRIVATE);
            if (sharedpreferences.contains(Name)) name = sharedpreferences.getString(Name, "");
            establishmentId = sharedpreferences.getString(UniqueID, "");
            GpsTracker gpsTracker;
            gpsTracker = new GpsTracker(this.getActivity());
            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
            } else {
                gpsTracker.showSettingsAlert();
            }
            lat = Double.toString(latitude);
            lon = Double.toString(longitude);
            feedback.setTextColor(BLUE);
            feedback.setText("Adding Customer...");
            feedback.setVisibility(View.VISIBLE);
            new JsonTask().execute("https://liezel.kenchlightyear.com/api/v1/customer");

        }
    }

    class JsonTask extends AsyncTask<String, String, String> {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("first", first);
                jsonParam.put("last", last);
                jsonParam.put("address", address);
                jsonParam.put("barangay", barangay);
                jsonParam.put("city", city);
                jsonParam.put("province", province);
                jsonParam.put("number", number);
                jsonParam.put("email", email);
                jsonParam.put("temperature", temperature);
                jsonParam.put("timestamp", ts);
                jsonParam.put("establishment", name);
                jsonParam.put("establishmentid", establishmentId);
                jsonParam.put("latitude", latitude);
                jsonParam.put("longitude", longitude);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                int code = conn.getResponseCode();
                if(code != 200) {
                    Log.i("JSON", jsonParam.toString());
                    Log.i("STATUS", String.valueOf(code));
                    Log.i("MSG", conn.getResponseMessage());
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }
        protected void onPostExecute(){
            View view = root;
            ((TextView) view.findViewById(R.id.etFirst)).setText("");
            ((TextView) view.findViewById(R.id.etLast)).setText("");
            ((TextView) view.findViewById(R.id.etAddress)).setText("");
            ((TextView) view.findViewById(R.id.etBarangay)).setText("");
            ((TextView) view.findViewById(R.id.etCity)).setText("");
            ((TextView) view.findViewById(R.id.etProvince)).setText("");
            ((TextView) view.findViewById(R.id.etNumber)).setText("");
            ((TextView) view.findViewById(R.id.etEmail)).setText("");
            ((TextView) view.findViewById(R.id.etTemperature)).setText("");
            feedback.setText("Customer added");
        }

    }
}