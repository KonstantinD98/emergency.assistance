package com.example.mergencyssistance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mergencyssistance.Entity.Patient;
import com.example.mergencyssistance.Entity.Pet;
import com.example.mergencyssistance.R;

import java.util.ArrayList;

public class PetAdapter extends ArrayAdapter<Pet> {
    Context context;
    ArrayList<Pet> petArrayList;


    public PetAdapter(Context context, ArrayList<Pet> petArrayList) {
        super(context, R.layout.pet_line, petArrayList);

        this.context = context;
        this.petArrayList = petArrayList;


    }
    public class Holder {
        TextView TvAnimal, TVbreed, TVName,TVowName;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pet pet = getItem(position);
        PetAdapter.Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new PetAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.pet_line, parent,false);

            viewHolder.TvAnimal = convertView.findViewById(R.id.TvAnimal);
            viewHolder.TVbreed = convertView.findViewById(R.id.TVbreed);
            viewHolder.TVName = convertView.findViewById(R.id.TVName);
            viewHolder.TVowName= convertView.findViewById(R.id.TVowName);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (PetAdapter.Holder) convertView.getTag();
        }
        viewHolder.TvAnimal.setText(pet.getAnimal());
        viewHolder.TVbreed.setText(pet.getBreed());
        viewHolder.TVName.setText(pet.getName());
        viewHolder.TVowName.setText(pet.getOwnerName());
        viewHolder.imageView.setImageResource(R.drawable.pet_icon);



        return convertView;

    }
}

