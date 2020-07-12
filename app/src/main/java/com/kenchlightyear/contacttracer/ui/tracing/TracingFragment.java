package com.kenchlightyear.contacttracer.ui.tracing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kenchlightyear.contacttracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TracingFragment extends Fragment {

    SharedPreferences sharedpreferences;
    public static final String establishment = "establishment";
    public static final String UniqueID = "uuid";

    String establishmentId;
    String first;
    String last;
    String address;
    String barangay;
    String city;
    String province;
    Long number;
    String email;
    Float temperature;
    String timestamp;

    RecyclerView recyclerView;
    private RecyclerView.Adapter customersAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TracingViewModel tracingViewModel;

    String [] customers;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tracingViewModel =
                ViewModelProviders.of(this).get(TracingViewModel.class);
        root = inflater.inflate(R.layout.fragment_tracing, container, false);
        tracingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Get(root);
            }
        });
        return root;
    }

    public void Get(View view) {
        recyclerView = view.findViewById(R.id.rCustomers);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        customers = new String[20];
        sharedpreferences = this.getActivity().getSharedPreferences(establishment,
                Context.MODE_PRIVATE);
        establishmentId = sharedpreferences.getString(UniqueID, "");
        new JsonTask().execute("https://liezel.kenchlightyear.com/api/v1/customers");
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
                jsonParam.put("establishmentid", establishmentId);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                InputStream stream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }
                JSONArray c = new JSONArray(buffer.toString());
                int len = c.length();
                for(int i=0;i<len;i++){
                    first = c.getJSONObject(i).getString("first");
                    last = c.getJSONObject(i).getString("last");
                    number = c.getJSONObject(i).getLong("number");
                    email = c.getJSONObject(i).getString("email");
                    customers[i] = first + " " + last + " 0" + number.toString() + " " + email;
                }
                int code = conn.getResponseCode();
                if(code != 200) {
                    Log.i("JSON", jsonParam.toString());
                    Log.i("STATUS", String.valueOf(code));
                    Log.i("MSG", conn.getResponseMessage());
                    Log.i("RESPONSE", buffer.toString());
                }
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }
        protected void onPostExecute(String ab){
            customersAdapter = new CustomersAdapater(customers);
            recyclerView.setAdapter(customersAdapter);
        }

    }
}