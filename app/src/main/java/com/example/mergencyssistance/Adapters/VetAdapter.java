package com.example.mergencyssistance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mergencyssistance.R;

import androidx.annotation.NonNull;

import com.example.mergencyssistance.Entity.Vet;

import java.util.ArrayList;

public class VetAdapter extends ArrayAdapter<Vet> {

    Context context;
    ArrayList<Vet> vetArrayList;


    public VetAdapter(Context context, ArrayList<Vet> vetArrayList) {
        super(context, R.layout.vet_line, vetArrayList);

        this.context = context;
        this.vetArrayList = vetArrayList;


    }
    public class Holder {
        TextView TvfNameVet, TVlNameVet, TVemailVet,TVphoneVet;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vet vet = getItem(position);
        Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vet_line, parent,false);

            viewHolder.TvfNameVet = convertView.findViewById(R.id.TvfNameVet);
            viewHolder.TVlNameVet = convertView.findViewById(R.id.TVlNameVet);
            viewHolder.TVemailVet = convertView.findViewById(R.id.TVemailVet);
            viewHolder.TVphoneVet= convertView.findViewById(R.id.TVphoneVet);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (Holder) convertView.getTag();
        }
        viewHolder.TvfNameVet.setText(vet.getFirstNameV());
        viewHolder.TVlNameVet.setText(vet.getLastNameV());
        viewHolder.TVemailVet.setText(vet.getEmailV());
        viewHolder.TVphoneVet.setText(vet.getPhoneV());
        viewHolder.imageView.setImageResource(R.drawable.vet_icon);



        return convertView;

    }
}

