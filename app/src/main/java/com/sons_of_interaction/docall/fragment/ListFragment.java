        package com.sons_of_interaction.docall.fragment;

        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v4.app.Fragment;

        import android.support.v4.app.NotificationCompat;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.sons_of_interaction.docall.R;

        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;


        import android.widget.AdapterView;
        import android.widget.ImageButton;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.sons_of_interaction.docall.BooksAdapter;
        import com.sons_of_interaction.docall.Db;
        import com.sons_of_interaction.docall.Visit;
        import com.sons_of_interaction.docall.activity.HomeActivity;
        import com.sons_of_interaction.docall.activity.LoginActivity;

        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.concurrent.TimeUnit;

        import static com.sons_of_interaction.docall.activity.HomeActivity.getDateDiff;
        import static com.sons_of_interaction.docall.activity.HomeActivity.notification;
        import static com.sons_of_interaction.docall.activity.HomeActivity.orarioVisita;

/*
/
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String fiscalCode;
    private View view;
    private ArrayList<Visit> visits;
    private BooksAdapter adapter;

    // private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /* Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
             * @param param1 Parameter 1.
            * @return A new instance of fragment ListFragment.
            */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fiscalCode = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        visits= LoginActivity.database.getListOfVisits(fiscalCode);

        adapter= new BooksAdapter(getActivity(),visits);

        view = inflater.inflate(R.layout.fragment_list,container,false);

        ((HomeActivity) getActivity()).setToolBar("Prenotazioni effettuate");
        ((HomeActivity) getActivity()).showFloatingActionButton();

        final ListView lvVisit = (ListView) view.findViewById(R.id.visit_list);

        lvVisit.setAdapter(adapter);

        lvVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Visit visit = (Visit) parent.getItemAtPosition(position);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                VisitFragment visitFragment = VisitFragment.newInstance(visit.getFcPatient(),visit.getDate());
                transaction.replace(((ViewGroup)getView().getParent()).getId(),
                        visitFragment,
                        visitFragment.getTag()).addToBackStack(null).commit();
            }
        });
        if(orarioVisita != null && notification == false){
            notification = true;
            Date orario1 = Calendar.getInstance().getTime();
            DateFormat format = new SimpleDateFormat("HH:mm");
            String oggi = format.format(orario1);
            try {
                orario1 = format.parse(oggi);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date orario2 = null;
            try {
                orario2 = format.parse(orarioVisita);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long diff = getDateDiff(orario1, orario2, TimeUnit.MILLISECONDS);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    sendNotification(view);
                }
            }, diff/2);

        }
        return view;
    }

    public void sendNotification(View view) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity());

        //Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(getContext(),LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.mipmap.app_icon);
        mBuilder.setContentTitle("DoCall");
        mBuilder.setContentText("Orario della visita alle: " + orarioVisita);

        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }



    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */

}
