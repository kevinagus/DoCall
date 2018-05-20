package com.sons_of_interaction.docall.activity;

        import android.content.Intent;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.view.MenuItemCompat;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AlertDialog;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.support.design.widget.NavigationView;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationServices;
        import com.sons_of_interaction.docall.Db;
        import com.sons_of_interaction.docall.Doctor;
        import com.sons_of_interaction.docall.Person;
        import com.sons_of_interaction.docall.R;
        import com.sons_of_interaction.docall.Visit;
        import com.sons_of_interaction.docall.fragment.BookFragment;
        import com.sons_of_interaction.docall.fragment.ListFragment;
        import com.sons_of_interaction.docall.fragment.PatientBookingsFragment;
        import com.sons_of_interaction.docall.fragment.PatientListFragment;
        import com.sons_of_interaction.docall.fragment.PositionFragment;
        import com.sons_of_interaction.docall.fragment.ProfileFragment;
        import com.sons_of_interaction.docall.fragment.RecapFragment;

        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.concurrent.TimeUnit;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BookFragment.OnFragmentInteractionListener {


    //prenotazione nuova visita
    public static Visit booking = new Visit();
    public static String orarioVisita = null;
    public static boolean notification = false;

        String codiceFiscale;
        NavigationView navigationView;
        TextView bookQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final String todayString = formatter.format(todayDate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todayString.equals(booking.getDate())) {
                    showAlertDialogButtonClicked(view);
                } else {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    BookFragment bookFragment = new BookFragment();
                    transaction.replace(R.id.content_home,
                            bookFragment,
                            bookFragment.getTag()).addToBackStack(null).commit();
                }
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bookQueue = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_prenotazioniPazienti));
        initializeCountDrawer();

        Intent intent = getIntent();
        codiceFiscale = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        if (personIsADoctor(codiceFiscale)) {
            hideItem(navigationView, R.id.nav_book);
        } else {
            hideItem(navigationView, R.id.nav_patients);
            hideItem(navigationView, R.id.nav_prenotazioniPazienti);

        }

        /*modifica del nome e cognome nel navigation drawer*/
        View header = navigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.name);
        userName.setText(username(codiceFiscale));

        ImageView profileImage = (ImageView) header.findViewById(R.id.profileImage);

        if(codiceFiscale.equalsIgnoreCase("PDDSVT64C06I452W")){
            profileImage.setImageResource(R.mipmap.salvatorepodda);
        } else if(codiceFiscale.equalsIgnoreCase("RSSMRA80A01B354W")){
            profileImage.setImageResource(R.mipmap.mariorossi);
        } else if(codiceFiscale.equalsIgnoreCase("MNCLCU85P50M082B")){
            profileImage.setImageResource(R.mipmap.luciamancini);
        } else if(codiceFiscale.equalsIgnoreCase("GSAKVN96M17I851G")){
            profileImage.setImageResource(R.mipmap.kevinagus);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProfileFragment profileFragment = ProfileFragment.newInstance(codiceFiscale);
        transaction.replace(R.id.content_home,
                profileFragment,
                profileFragment.getTag()).commit();

    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profilo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            ProfileFragment profileFragment = ProfileFragment.newInstance(codiceFiscale);
            transaction.replace(R.id.content_home,
                    profileFragment,
                    profileFragment.getTag()).commit();
        } else if (id == R.id.nav_book) {
            ListFragment listFragment = ListFragment.newInstance(codiceFiscale);
            transaction.replace(R.id.content_home,
                    listFragment,
                    listFragment.getTag()).addToBackStack(null).commit();
        }else if (id == R.id.nav_patients){
            PatientListFragment patientsFragment = PatientListFragment.newInstance(codiceFiscale);
            transaction.replace(R.id.content_home,
                    patientsFragment,
                    patientsFragment.getTag()).addToBackStack(null).commit();
        }else if(id == R.id.nav_prenotazioniPazienti){
            PatientBookingsFragment patientBookingsFragment = new PatientBookingsFragment();
            transaction.replace(R.id.content_home,
                    patientBookingsFragment,
                    patientBookingsFragment.getTag()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(String symptoms) {

        booking.setSymptoms(symptoms);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PositionFragment positionFragment = PositionFragment.newInstance(codiceFiscale);
        transaction.replace(R.id.content_home,
                positionFragment,
                positionFragment.getTag()).addToBackStack(null).commit();
    }


    public void setToolBar(String title) {
        getSupportActionBar().setTitle(title);
    }

    private String username(String codiceFiscale) {
        for(Person p:LoginActivity.database.poddasPatients()){
            if(p.getFiscalCode().equalsIgnoreCase(codiceFiscale)){
                return p.getName()+" "+p.getSurname();
            }
        }

        for(Person p:LoginActivity.database.agrestisPatients()){
            if(p.getFiscalCode().equalsIgnoreCase(codiceFiscale)){
                return p.getName()+" "+p.getSurname();
            }
        }

        for(Doctor p:LoginActivity.database.getListOfDoctors()){
            if(p.getFiscalCode().equalsIgnoreCase(codiceFiscale)){
                return p.getName()+" "+p.getSurname();
            }
        }
        return null;
    }

    public void showFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    public void hideFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }

    public void hideItem(NavigationView navigationView, int resource){
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(resource).setVisible(false);
    }

    private boolean personIsADoctor(String person) {
        for(Doctor d:LoginActivity.database.getListOfDoctors()){
            if(d.getFiscalCode().equalsIgnoreCase(person)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Avviso");
        builder.setMessage("Prenotazione già effettuata per il giorno corrente");

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initializeCountDrawer(){
        //Gravity property aligns the text
        bookQueue.setGravity(Gravity.CENTER_VERTICAL);
        bookQueue.setTypeface(null, Typeface.BOLD);
        bookQueue.setTextColor(getResources().getColor(R.color.colorAccent,this.getTheme()));
        bookQueue.setText("3");
    }
}
