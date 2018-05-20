package com.sons_of_interaction.docall.fragment;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sons_of_interaction.docall.Patient;
import com.sons_of_interaction.docall.R;
import com.sons_of_interaction.docall.activity.LoginActivity;


import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckPrenotazioneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckPrenotazioneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String cf;
    Patient patient;
    TextView nome,cognome,sintomi,indirizzo;
    EditText orario;
    ImageButton mappa;
    Button conferma;
    ImageView immagineProfilo;


    public CheckPrenotazioneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cf Parameter 1.
     * @return A new instance of fragment CheckPrenotazioneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckPrenotazioneFragment newInstance(String cf) {
        CheckPrenotazioneFragment fragment = new CheckPrenotazioneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, cf);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cf = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_prenotazione,container,false);
        patient = registeredUser(cf);
        nome = (TextView) view.findViewById(R.id.nome);
        cognome = (TextView) view.findViewById(R.id.cognome);
        sintomi = (TextView) view.findViewById(R.id.sintomi);
        indirizzo = (TextView) view.findViewById(R.id.indirizzo);
        mappa = (ImageButton) view.findViewById(R.id.mappa);
        orario = (EditText) view.findViewById(R.id.visitTime);
        conferma = (Button) view.findViewById(R.id.buttonConferma);
        immagineProfilo = (ImageView) view.findViewById(R.id.immagineProfilo);

        if(patient.getName().equalsIgnoreCase("Mario")){
            immagineProfilo.setImageResource(R.mipmap.mariorossi);
        }else if(patient.getName().equalsIgnoreCase("Lucia")){
            immagineProfilo.setImageResource(R.mipmap.luciamancini);
        }else if(patient.getName().equalsIgnoreCase("Kevin")){
            immagineProfilo.setImageResource(R.mipmap.kevinagus);
        }

        if(patient.getPrenotazioneConfermata()){
            conferma.setVisibility(View.GONE);
            orario.setText("15 : 30 h");
            orario.setTextSize(22);
        }

        nome.setText(patient.getName());
        cognome.setText(patient.getSurname());
        sintomi.setText("Forte mal di testa e giramenti");
        indirizzo.setText("Cagliari, Via Basilicata 39B");
        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        mappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientPositionFragment patientPositionFragment = new PatientPositionFragment();
                transaction.replace(((ViewGroup)getView().getParent()).getId(),
                        patientPositionFragment,
                        patientPositionFragment.getTag()).addToBackStack(null).commit();
            }
        });

        orario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        orario.setText(selectedHour + " : " + selectedMinute + " h");
                        orario.setTextSize(22);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Orario visita previsto:");
                mTimePicker.show();
            }
        });

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient.setPrenotazioneConfermata(true);
                PatientBookingsFragment patientBookingsFragment = new PatientBookingsFragment();
                transaction.replace(R.id.content_home,
                        patientBookingsFragment,
                        patientBookingsFragment.getTag()).addToBackStack(null).commit();
            }
        });
        return view;
    }

    public Patient registeredUser(String codiceFiscale){

        for(Patient p: LoginActivity.database.patients()){
            if(p.getFiscalCode().equalsIgnoreCase(codiceFiscale)) return p;
        }
        return null;
    }
}
