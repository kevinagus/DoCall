package com.sons_of_interaction.docall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sons_of_interaction.docall.BookingsAdapter;
import com.sons_of_interaction.docall.BooksAdapter;
import com.sons_of_interaction.docall.Patient;
import com.sons_of_interaction.docall.Person;
import com.sons_of_interaction.docall.R;
import com.sons_of_interaction.docall.activity.HomeActivity;
import com.sons_of_interaction.docall.activity.LoginActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientBookingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientBookingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<Patient> patients;
    private BookingsAdapter adapter;


    public PatientBookingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientBookingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientBookingsFragment newInstance(String param1, String param2) {
        PatientBookingsFragment fragment = new PatientBookingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_patient_bookings, container, false);

        ((HomeActivity) getActivity()).setToolBar("Visite da effettuare");
        ((HomeActivity) getActivity()).hideFloatingActionButton();

        final ListView lvVisit = (ListView) view.findViewById(R.id.bookings_list);

        patients = LoginActivity.database.patients();

        adapter= new BookingsAdapter(getActivity(),patients);

        lvVisit.setAdapter(adapter);

        lvVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Patient patient = (Patient) parent.getItemAtPosition(position);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                CheckPrenotazioneFragment checkPrenotazioneFragment = CheckPrenotazioneFragment.newInstance(patient.getFiscalCode());
                transaction.replace(((ViewGroup)getView().getParent()).getId(),
                        checkPrenotazioneFragment,
                        checkPrenotazioneFragment.getTag()).addToBackStack(null).commit();
            }
            });

        return view;
    }

}
