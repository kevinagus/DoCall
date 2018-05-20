
        package com.sons_of_interaction.docall.fragment;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;


        import com.sons_of_interaction.docall.Doctor;
        import com.sons_of_interaction.docall.R;
        import com.sons_of_interaction.docall.Visit;
        import com.sons_of_interaction.docall.activity.HomeActivity;
        import com.sons_of_interaction.docall.activity.LoginActivity;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;

        import static com.sons_of_interaction.docall.activity.HomeActivity.booking;
        import static com.sons_of_interaction.docall.activity.HomeActivity.notification;
        import static com.sons_of_interaction.docall.activity.HomeActivity.orarioVisita;

        /* A simple {@link Fragment} subclass.
        * Use the {@link VisitFragment#newInstance} factory method to
        * create an instance of this fragment.
        */
public class VisitFragment extends Fragment {
            // TODO: Rename parameter arguments, choose names that match
            // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
            private static final String ARG_PARAM1 = "param1";
            private static final String ARG_PARAM2 = "param2";

            // TODO: Rename and change types of parameters
            private String fcPatient;
            private String date;

            Button btnAnnulla;

            public VisitFragment() {
                // Required empty public constructor
            }

            /* Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
                     * @param param1 Parameter 1.
                    * @param param2 Parameter 2.
                    * @return A new instance of fragment VisitFragment.
                    */
            // TODO: Rename and change types and number of parameters
            public static VisitFragment newInstance(String param1, String param2) {
                VisitFragment fragment = new VisitFragment();
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
                    fcPatient = getArguments().getString(ARG_PARAM1);
                    date = getArguments().getString(ARG_PARAM2);
                }
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {

                View view = inflater.inflate(R.layout.fragment_visit, container, false);

                TextView tvSymptoms = (TextView) view.findViewById(R.id.visit_symptoms);
                TextView tvDoctor = (TextView) view.findViewById(R.id.visit_doctor);
                TextView tvDate = (TextView) view.findViewById(R.id.visit_date);
                TextView address = (TextView) view.findViewById(R.id.visit_address);

                btnAnnulla = (Button) view.findViewById(R.id.annullaPrenotazione);

                final Visit v = getVisitFromFcAndDate(fcPatient, date);
                tvSymptoms.setText(v.getSymptoms());
                tvDoctor.setText(doctorSurnameAndName(v.getDoctor()));
                tvDate.setText(v.getDate());
                address.setText(v.getAddress());

                ((HomeActivity) getActivity()).setToolBar("Dettaglio visita");
                ((HomeActivity) getActivity()).hideFloatingActionButton();

                Date orarioCorrente = Calendar.getInstance().getTime();
                DateFormat format = new SimpleDateFormat("HH:mm");
                String orarioCurr = format.format(orarioCorrente);
                Date today = Calendar.getInstance().getTime();
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                String oggi = f.format(today);

                if(orarioVisita != null){
                    if((orarioCurr.compareTo(orarioVisita) <= 0) && (oggi.equals(tvDate.getText()))) {
                        final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        btnAnnulla.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeVisit(fcPatient, date);
                                ListFragment listFragment = ListFragment.newInstance(fcPatient);
                                transaction.replace(((ViewGroup) getView().getParent()).getId(),
                                        listFragment,
                                        listFragment.getTag()).addToBackStack(null).commit();
                                notification = false;
                            }
                        });
                    }else{

                        btnAnnulla.setVisibility(View.GONE);
                    }
                }
                else{
                    btnAnnulla.setVisibility(View.GONE);
                }
                return view;
            }

            public Visit getVisitFromFcAndDate(String fcPatient, String date) {
                for (Visit v : LoginActivity.database.getListOfVisits(fcPatient)) {
                    if (v.getDate().equals(date)) {
                        return v;
                    }
                }
                return null;
            }

            public String doctorSurnameAndName(String fiscalCode) {
                for (Doctor d : LoginActivity.database.getListOfDoctors()) {
                    if (d.getFiscalCode().equalsIgnoreCase(fiscalCode)) {
                        return d.getSurname() + " " + d.getName();
                    }
                }
                return null;
            }

            public void removeVisit(String cf, String data){
                Visit v = getVisitFromFcAndDate(cf,data);
                ArrayList<Visit> visits = LoginActivity.database.getListOfVisits(cf);
                ArrayList<Visit> newvisits = new ArrayList<>();

                for(Visit corr : visits){
                    if(!(corr.getDate().equals(v.getDate()) && corr.getFcPatient().equalsIgnoreCase(v.getFcPatient()))){
                        newvisits.add(corr);
                    }
                }

                LoginActivity.database.updateVisits(newvisits);
            }
        }
