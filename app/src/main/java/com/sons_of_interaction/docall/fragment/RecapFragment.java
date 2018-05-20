package com.sons_of_interaction.docall.fragment;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sons_of_interaction.docall.Doctor;
import com.sons_of_interaction.docall.R;
import com.sons_of_interaction.docall.Visit;
import com.sons_of_interaction.docall.activity.LoginActivity;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.sons_of_interaction.docall.activity.HomeActivity.booking;
import static com.sons_of_interaction.docall.activity.HomeActivity.getDateDiff;
import static com.sons_of_interaction.docall.activity.HomeActivity.orarioVisita;

public class RecapFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String mDate,mFc,mAddress,mDoctor,mSymptoms;

    TextView tvSymptoms,tvAddress,tvDate,tvDoctor;
    Button btnConferma;



    public RecapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recap,container,false);
        tvSymptoms = (TextView) view.findViewById(R.id.symptomsText);
        tvAddress = (TextView) view.findViewById(R.id.addressText);
        tvDate = (TextView) view.findViewById(R.id.dateText);
        tvDoctor = (TextView) view.findViewById(R.id.doctorText);
        mSymptoms = booking.getSymptoms();
        mAddress = booking.getAddress();
        mDate = booking.getDate();
        mDoctor = doctorSurnameAndName(booking.getDoctor());
        mFc = booking.getFcPatient();
        tvSymptoms.setText(mSymptoms);
        tvAddress.setText(mAddress);
        tvDate.setText(mDate);
        tvDoctor.setText(mDoctor);

        Date orario = Calendar.getInstance().getTime();
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        String hour = f.format(orario);

        final long millisToAdd = 10_000;

        DateFormat format = new SimpleDateFormat("HH:mm");

        Date d = null;
        try {
            d = format.parse(hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orarioVisita = f.format((d.getTime() + millisToAdd))+"";

        btnConferma = (Button) view.findViewById(R.id.confermaPrenotazione);
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        btnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.database.addVisit(booking);
                NavigationView navigationView= (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(1).setActionView(R.layout.menu_dot);
                ListFragment listFragment = ListFragment.newInstance(mFc);
                transaction.replace(((ViewGroup)getView().getParent()).getId(),
                        listFragment,
                        listFragment.getTag()).addToBackStack(null).commit();

            }
        });

        return view;
    }

    public String doctorSurnameAndName(String fiscalCode) {
        for (Doctor d : LoginActivity.database.getListOfDoctors()) {
            if (d.getFiscalCode().equalsIgnoreCase(fiscalCode)) {
                return d.getSurname() + " " + d.getName();
            }
        }
        return null;
    }

}
