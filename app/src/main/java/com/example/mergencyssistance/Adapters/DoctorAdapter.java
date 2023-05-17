package com.example.mergencyssistance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mergencyssistance.Entity.Doctor;

import com.example.mergencyssistance.R;

import java.util.ArrayList;

public class DoctorAdapter extends ArrayAdapter<Doctor> {

    Context context;
    ArrayList<Doctor> doctorArrayList;


    public DoctorAdapter(Context context, ArrayList<Doctor> doctorArrayList) {
        super(context, R.layout.doctor_line, doctorArrayList);

        this.context = context;
        this.doctorArrayList = doctorArrayList;


    }
    public class Holder {
        TextView TvfNameD, TVlNameD, TVphoneD,TVemailD, TVspeciality;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);
        DoctorAdapter.Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new DoctorAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.doctor_line, parent,false);

            viewHolder.TvfNameD = convertView.findViewById(R.id.TvfNameD);
            viewHolder.TVlNameD = convertView.findViewById(R.id.TVlNameD);
            viewHolder.TVphoneD = convertView.findViewById(R.id.TVphoneD);
            viewHolder.TVemailD= convertView.findViewById(R.id.TVemailD);
            viewHolder.TVspeciality = convertView.findViewById(R.id.TVspeciality);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (DoctorAdapter.Holder) convertView.getTag();
        }
        viewHolder.TvfNameD.setText(doctor.getFirstNameD());
        viewHolder.TVlNameD.setText(doctor.getLastNameD());
        viewHolder.TVphoneD.setText(doctor.getEmailD());
        viewHolder.TVemailD.setText(doctor.getPhone());
        viewHolder.TVspeciality.setText(doctor.getSpeciality());
        viewHolder.imageView.setImageResource(R.drawable.doctor_icon);



        return convertView;

    }
}

