package com.example.petmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PetAdapter extends ArrayAdapter<FosterInfo> {
    private int resourceId;

    public PetAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<FosterInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FosterInfo info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView id = view.findViewById(R.id.id);
        TextView pet_id = view.findViewById(R.id.pet_id_in_home);
        TextView health = view.findViewById(R.id.health);
        Button detail = view.findViewById(R.id.detail_btn);

        id.setText(Integer.toString(info.getFosterId()));
        pet_id.setText(Integer.toString(info.getPetId()));
        health.setText(info.getHealth());
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) getContext();
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra("fosterInfo", info);
                activity.startActivity(intent);
            }
        });
        return view;
    }
}
