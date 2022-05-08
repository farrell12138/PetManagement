package com.example.petmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PetAdapter1 extends ArrayAdapter<Pet> {
    private int resourceId;
    public PetAdapter1(@NonNull Context context, int textViewResourceId, @NonNull List<Pet> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pet pet = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView id = view.findViewById(R.id.pet_id_in_user_info);
        TextView pet_id = view.findViewById(R.id.pet_name);
        TextView fosterStatus = view.findViewById(R.id.status);

        id.setText(Integer.toString(pet.getId()));
        pet_id.setText(pet.getName());
        fosterStatus.setText(pet.isFoster());

        return view;
    }
}
