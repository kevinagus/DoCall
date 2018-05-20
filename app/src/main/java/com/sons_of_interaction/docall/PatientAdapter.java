package com.sons_of_interaction.docall;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 15/02/2018.
 */

public class PatientAdapter extends ArrayAdapter {

    private ArrayList<Person> patients;
    private Context context;

    public PatientAdapter(Activity context, ArrayList<Person> patients){
        super(context,R.layout.fragment_patient_list);
        this.patients=patients;
        this.context=context;
    }

    @Override
    public int getCount(){
        return patients.size();
    }

    @Override
    public Object getItem(int position){
        return patients.get(position);
    }

    @Override
    public long getItemId(int position){
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position, View v, ViewGroup vg){

        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.patient_item, vg,false);
        }

        ImageView ivPatient = (ImageView) v.findViewById(R.id.list_image);

        if(patients.get(position).getName().equals("Mario")){
            ivPatient.setImageResource(R.mipmap.mariorossi);
        }else if(patients.get(position).getName().equals("Lucia")){
            ivPatient.setImageResource(R.mipmap.luciamancini);
        }else if(patients.get(position).getName().equals("Kevin")){
            ivPatient.setImageResource(R.mipmap.kevinagus);
        }

        TextView mtextView = (TextView) v.findViewById(R.id.patient_info);
        mtextView.setText(patients.get(position).getName().concat(" ").concat(patients.get(position).getSurname()));
        return v;
    }

}