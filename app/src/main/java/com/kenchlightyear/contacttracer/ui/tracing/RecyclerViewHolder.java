package com.kenchlightyear.contacttracer.ui.tracing;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kenchlightyear.contacttracer.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView number;
    private TextView email;

    public RecyclerViewHolder(@NonNull final View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.etCustomer);
        number = itemView.findViewById(R.id.etNumber);
        email = itemView.findViewById(R.id.etEmail);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RecyclerView", "onClick：" + getAdapterPosition());
            }
        });
    }

    public TextView getName() {
        return name;
    }

    public TextView getNumber() {
        return number;
    }

    public TextView getEmail() {
        return email;
    }
}
