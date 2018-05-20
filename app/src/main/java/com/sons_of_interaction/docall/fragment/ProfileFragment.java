package com.sons_of_interaction.docall.fragment;


        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.RequiresApi;
        import android.support.v4.app.Fragment;
        import android.support.v4.view.ViewCompat;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toolbar;

        import com.sons_of_interaction.docall.Db;
        import com.sons_of_interaction.docall.Doctor;
        import com.sons_of_interaction.docall.Person;
        import com.sons_of_interaction.docall.R;
        import com.sons_of_interaction.docall.activity.HomeActivity;
        import com.sons_of_interaction.docall.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String p;
    private Person person;
    private Doctor doctor;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String person) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("Person",person);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).hideFloatingActionButton();
        if (getArguments() != null) {
            p = getArguments().getString("Person");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        ((HomeActivity) getActivity()).setToolBar("Profilo");
        ((HomeActivity) getActivity()).hideFloatingActionButton();


        TextView tvName=(TextView) view.findViewById(R.id.Nome);
        TextView tvSurname=(TextView) view.findViewById(R.id.Cognome);
        TextView tvFiscalCode=(TextView) view.findViewById(R.id.CodiceFiscale);
        TextView tvDateOfBirth=(TextView) view.findViewById(R.id.DataDiNascita);
        TextView tvDoctor=(TextView) view.findViewById(R.id.Dottore);
        TextView tvTitleDoctor = (TextView) view.findViewById(R.id.MedicoField);

        ImageView ivPerson = (ImageView) view.findViewById(R.id.imageView);

        if(personIsADoctor(p)){
            doctor=registeredDoctor(p);
            tvName.setText(doctor.getName());
            tvSurname.setText(doctor.getSurname());
            tvFiscalCode.setText(doctor.getFiscalCode());
            tvDateOfBirth.setText(doctor.getBirthDate());
            tvDoctor.setVisibility(View.GONE);
            tvTitleDoctor.setVisibility(View.GONE);

            if(doctor.getName().equals("Salvatore")) {
                ivPerson.setImageResource(R.mipmap.salvatorepodda);
            }
        }
        else{
            person=registeredUser(p);
            tvName.setText(person.getName());
            tvSurname.setText(person.getSurname());
            tvFiscalCode.setText(person.getFiscalCode());
            tvDateOfBirth.setText(person.getBirthDate());
            tvDoctor.setText("Dott. ".concat(person.getDoctor().getSurname()).concat(" ").concat(person.getDoctor().getName()));

            if(person.getName().equals("Mario")){
                ivPerson.setImageResource(R.mipmap.mariorossi);
            }else if(person.getName().equals("Lucia")){
                ivPerson.setImageResource(R.mipmap.luciamancini);
            }else if(person.getName().equals("Kevin")){
                ivPerson.setImageResource(R.mipmap.kevinagus);
            }
        }

        return view;
    }

    public Person registeredUser(String codiceFiscale){

        for(Person p: LoginActivity.database.poddasPatients()){
            if(p.getFiscalCode().equalsIgnoreCase(codiceFiscale)) return p;
        }
        for(Person p: LoginActivity.database.agrestisPatients())
            if (p.getFiscalCode().equalsIgnoreCase(codiceFiscale)) return p;

        return null;
    }

    private boolean personIsADoctor(String person) {
        for(Doctor d: LoginActivity.database.getListOfDoctors()){
            if(d.getFiscalCode().equalsIgnoreCase(person)){
                return true;
            }
        }
        return false;
    }

    public Doctor registeredDoctor(String codiceFiscale){

        for(Doctor d: LoginActivity.database.getListOfDoctors()){
            if (d.getFiscalCode().equalsIgnoreCase(codiceFiscale)) return d;

        }
        return null;
    }
}
