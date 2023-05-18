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
import com.example.mergencyssistance.R;

import java.util.ArrayList;

public class PatientAdapter extends ArrayAdapter<Patient> {
    Context context;
    ArrayList<Patient> patientArrayList;


    public PatientAdapter(Context context, ArrayList<Patient> patientArrayList) {
        super(context, R.layout.patient_line, patientArrayList);

        this.context = context;
        this.patientArrayList = patientArrayList;


    }
    public class Holder {
        TextView TvfNameP, TVlNameP, TVemailP,TVphoneP;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Patient patient = getItem(position);
        PatientAdapter.Holder viewHolder;

        if (convertView == null)
        {
            viewHolder = new PatientAdapter.Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.patient_line, parent,false);

            viewHolder.TvfNameP = convertView.findViewById(R.id.TvfNameP);
            viewHolder.TVlNameP = convertView.findViewById(R.id.TVlNameP);
            viewHolder.TVphoneP= convertView.findViewById(R.id.TVphoneP);
            viewHolder.TVemailP = convertView.findViewById(R.id.TVemailP);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (PatientAdapter.Holder) convertView.getTag();
        }
        viewHolder.TvfNameP.setText(patient.getFirstNameP());
        viewHolder.TVlNameP.setText(patient.getLastNameP());
        viewHolder.TVphoneP.setText(patient.getPhoneP());
        viewHolder.TVemailP.setText(patient.getEmailP());
        viewHolder.imageView.setImageResource(R.drawable.patient_icon);



        return convertView;

    }
}

