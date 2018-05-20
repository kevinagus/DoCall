package com.sons_of_interaction.docall;

import android.app.Activity;
import android.content.Context;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class BookingsAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Patient> patients;

    public BookingsAdapter(Activity context, ArrayList<Patient> patients) {
        super(context, R.layout.fragment_patient_bookings);
        this.context = context;
        this.patients = patients;
    }

    @Override
    public int getCount() {
        return patients.size();
    }

    @Override
    public Object getItem(int position) {
        return patients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View v, ViewGroup vg) {

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.item, vg, false);
        }
        TextView mTextView = (TextView) v.findViewById(R.id.dataVisita);
        ConstraintLayout itemVisit = (ConstraintLayout) v.findViewById(R.id.itemVisit);
        itemVisit.setBackgroundColor(0x11FF0000);
        if(patients.get(position).getPrenotazioneConfermata()){
            itemVisit.setBackgroundColor(0x1100FF00);
        }
        mTextView.setText(patients.get(position).getName() + " " + patients.get(position).getSurname());
        return v;
    }
}
