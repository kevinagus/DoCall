package com.sons_of_interaction.docall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sons_of_interaction.docall.Db;
import com.sons_of_interaction.docall.PatientAdapter;
import com.sons_of_interaction.docall.Person;
import com.sons_of_interaction.docall.R;
import com.sons_of_interaction.docall.activity.HomeActivity;

import java.util.ArrayList;

       /* A simple {@link Fragment} subclass.
        * Use the {@link PatientListFragment#newInstance} factory method to
        * create an instance of this fragment.
        */
public class PatientListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String doctor;
    private View view;
    private ArrayList<Person> patients = new ArrayList<>();
    private Db database = new Db();
    private PatientAdapter adapter;


    public PatientListFragment() {
        // Required empty public constructor
    }

    /* Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PatientListFragment.
            */
    // TODO: Rename and change types and number of parameters
    public static PatientListFragment newInstance(String param1) {
        PatientListFragment fragment = new PatientListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        patients = doctorsPatients(doctor);

        adapter = new PatientAdapter(getActivity(),patients);

        view=inflater.inflate(R.layout.fragment_patient_list, container, false);

        ((HomeActivity) getActivity()).setToolBar("Pazienti");

        final ListView lvVisit = (ListView) view.findViewById(R.id.patients_list);

        lvVisit.setAdapter(adapter);

        lvVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Person patient = (Person) parent.getItemAtPosition(position);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ProfileFragment patientFragment = ProfileFragment.newInstance(patient.getFiscalCode());
                transaction.replace(((ViewGroup)getView().getParent()).getId(),
                        patientFragment,
                        patientFragment.getTag()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public ArrayList<Person> doctorsPatients(String doctor){

        ArrayList<Person> visits = new ArrayList<>();

        for(Person p: database.agrestisPatients()){
            if(p.getDoctor().getFiscalCode().toString().equalsIgnoreCase(doctor)){
                visits.add(p);
            }
        }

        for(Person p: database.poddasPatients()){
            if(p.getDoctor().getFiscalCode().toString().equalsIgnoreCase(doctor)){
                visits.add(p);
            }
        }

        return visits;
    }

}