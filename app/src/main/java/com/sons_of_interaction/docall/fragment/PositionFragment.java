        package com.sons_of_interaction.docall.fragment;

        import android.content.Context;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.Manifest;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.pm.PackageManager;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.content.ContextCompat;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.Button;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapView;
        import com.google.android.gms.maps.MapsInitializer;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.LatLng;
        import com.sons_of_interaction.docall.Person;
        import com.sons_of_interaction.docall.R;
        import com.sons_of_interaction.docall.activity.HomeActivity;
        import com.sons_of_interaction.docall.activity.LoginActivity;

import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

        import static android.content.Context.LOCATION_SERVICE;
        import static com.sons_of_interaction.docall.activity.HomeActivity.booking;

public class PositionFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mCf;


    MapView mapView;
    GoogleMap googleMap;
    LocationManager locationManager;
    Location location;
    Double latitude, longitude;
    Button btnBook;
    Geocoder geocoder;
    List<Address> addresses;
    String address;

    public PositionFragment() {
    }

    public static PositionFragment newInstance(String cf) {
        PositionFragment fragment = new PositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, cf);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCf = getArguments().getString(ARG_PARAM1);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position, container, false);
        ((HomeActivity)getActivity()).setToolBar("Riepilogo prenotazione");
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        mapView = (MapView) view.findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(this);

        btnBook = (Button) view.findViewById(R.id.buttonPrenota);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geocoder=new Geocoder(PositionFragment.this.getActivity(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude,longitude,1);

                    address = addresses.get(0).getAddressLine(0);

                    booking.setAddress(getLocationAddress());

                    booking.setFcPatient(mCf);

                    Date todayDate = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String todayString = formatter.format(todayDate);

                    booking.setDate(todayString);
                    booking.setDoctor(getDoctorOfPatient(mCf));

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    RecapFragment recapFragment = new RecapFragment();
                    transaction.replace(((ViewGroup)getView().getParent()).getId(),
                            recapFragment,
                            recapFragment.getTag()).addToBackStack(null).commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        LatLng cameraPosition;
        googleMap = mMap;
        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                //Request location updates:
                googleMap.setMyLocationEnabled(true);
                /*locationManager = (LocationManager) getActivity().getSystemService(serviceString);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                cameraPosition = new LatLng(latitude,longitude);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition,15));*/
                locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
                List<String> providers = locationManager.getProviders(true);

                for (String provider : providers) {
                    Location l = locationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (location == null || l.getAccuracy() < location.getAccuracy()) {
                        // Found best last known location: %s", l);
                        location = l;
                    }
                }

                latitude = location.getLatitude();
                longitude = location.getLongitude();
                cameraPosition = new LatLng(latitude,longitude);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition,15));
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

// Show an explanation to the user *asynchronously* — don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION},1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {
                }
                return;
            }

        }
    }

    public String getLocationAddress(){
        return address;
    }

    public String getDoctorOfPatient(String fcPatient){
        for(Person p: LoginActivity.database.poddasPatients()){
            if(p.getFiscalCode().equalsIgnoreCase(fcPatient)){
                return p.getDoctor().getFiscalCode();
            }
        }

        for(Person p: LoginActivity.database.agrestisPatients()){
            if(p.getFiscalCode().equalsIgnoreCase(fcPatient)){
                return p.getDoctor().getFiscalCode();
            }
        }
        return null;
    }
}
