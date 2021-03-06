        package com.sons_of_interaction.docall.fragment;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;

        import com.sons_of_interaction.docall.R;
        import com.sons_of_interaction.docall.activity.HomeActivity;

        /* A simple {@link Fragment} subclass.
        * Activities that contain this fragment must implement the
        * {@link BookFragment.OnFragmentInteractionListener} interface
        * to handle interaction events.
        * Use the {@link BookFragment#newInstance} factory method to
        * create an instance of this fragment.
        */
public class BookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BookFragment() {
        // Required empty public constructor
    }

    /* Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
             * @param param1 Parameter 1.
            * @param param2 Parameter 2.
            * @return A new instance of fragment BookFragment.
            */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
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

    EditText symptoms;
    Button btnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_book, container, false);
        ((HomeActivity) getActivity()).setToolBar("Prenota");
        ((HomeActivity) getActivity()).hideFloatingActionButton();

        symptoms = (EditText)view.findViewById(R.id.Sintomi);

        btnNext = (Button)view.findViewById(R.id.buttonProcedi);

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onButtonPressed(symptoms.getText().toString());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String symptoms) {
        if (mListener != null) {
            mListener.onFragmentInteraction(symptoms);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String symptoms);
    }
}