
        package com.sons_of_interaction.docall.activity;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;

        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.sons_of_interaction.docall.Db;
        import com.sons_of_interaction.docall.Doctor;
        import com.sons_of_interaction.docall.Person;
        import com.sons_of_interaction.docall.R;

        import java.util.ArrayList;

        /* A login screen that offers login via email/password.
        */
public class LoginActivity extends AppCompatActivity{

    /* Id to identity READ_CONTACTS permission request.
            */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    public static final String EXTRA_MESSAGE = "Message from LoginActivity";
    public static final String DOCTOR_MESSAGE = "Person is a doctor";


    // UI references.
    private EditText mPasswordView,mCodiceFiscaleView;
    private View mProgressView;
    private View mLoginFormView;

    //richiamare il Db
    public static Db database = new Db();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        // Set up the login form.
        mCodiceFiscaleView = (EditText)findViewById(R.id.codice_fiscale);
        mPasswordView = (EditText)findViewById(R.id.password);

        Button btnLogin = (Button)findViewById(R.id.sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String codiceFiscale = mCodiceFiscaleView.getText().toString();
                String psw = mPasswordView.getText().toString();
                boolean cfValid, pswValid;
                int rUser;
                cfValid = isFiscalCodeValid(codiceFiscale);
                pswValid = isPasswordValid(psw);
                rUser = registeredUser(codiceFiscale,psw);

                Intent profiloIntent = new Intent(LoginActivity.this,HomeActivity.class);

                if(!cfValid && !pswValid){
                    mCodiceFiscaleView.setError("Codice fiscale non di 16 cifre");
                    mPasswordView.setError("La password deve avere almeno 5 cifre");
                }
                else if(!cfValid){
                    mCodiceFiscaleView.setError("Codice fiscale non di 16 cifre");
                }
                else if(!pswValid){
                    mPasswordView.setError("La password deve avere almeno 5 cifre");
                }
                else{
                    if(rUser==0){
                        if(personIsADoctor(codiceFiscale)){
                            profiloIntent.putExtra(DOCTOR_MESSAGE,"Doctor");
                        }
                        profiloIntent.putExtra(EXTRA_MESSAGE,codiceFiscale);
                        startActivity(profiloIntent);
                        Toast.makeText(LoginActivity.this,"Login avvenuto con successo", Toast.LENGTH_SHORT).show();
                    }
                    else if(rUser==1){
                        mPasswordView.setError("Password sbagliata");
                    }
                    else if(rUser==2){
                        mCodiceFiscaleView.setError("Codice fiscale sbagliato");
                    }
                    else if(rUser==3){
                        mCodiceFiscaleView.setError("Codice fiscale sbagliato");
                        mPasswordView.setError("Password sbagliata");
                    }
                }
            }
        });

        Button btnSignIn = (Button)findViewById(R.id.register);
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    //Return 0 quando cf e psw sono validi entrambi
    //Return 1 quando cf valido ma non psw
    //Return 2 quando cf non valido ma psw si
    //Return 3 in altri casi
    private int registeredUser(String codiceFiscale,String password) {

        ArrayList<Person> listPodda = database.poddasPatients();
        ArrayList<Person> listAgresti = database.agrestisPatients();
        ArrayList<Doctor> doctorArrayList = database.getListOfDoctors();

        for (Person p : listPodda) {
            if (p.getFiscalCode().equalsIgnoreCase(codiceFiscale)) {
                if (p.getPassword().equals(password)) {
                    return 0;
                }
                else{
                    return 1;
                }
            } else if (p.getPassword().equals(password)) {
                return 2;
            }
        }

        for (Person p : listAgresti) {
            if (p.getFiscalCode().equalsIgnoreCase(codiceFiscale)) {
                if (p.getPassword().equals(password)) {
                    return 0;
                }
                else{
                    return 1;
                }
            } else if (p.getPassword().equals(password)) {
                return 2;
            }
        }

        for (Doctor d : doctorArrayList) {
            if (d.getFiscalCode().equalsIgnoreCase(codiceFiscale)) {
                if (d.getPassword().equals(password)) {
                    return 0;
                }
                else{
                    return 1;
                }
            } else if (d.getPassword().equals(password)) {
                return 2;
            }
        }
        return 3;
    }

    private boolean isFiscalCodeValid(String fiscalCode) {

        return fiscalCode.length()==16;
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    private boolean personIsADoctor(String person) {
        for(Doctor d:database.getListOfDoctors()){
            if(d.getFiscalCode().equalsIgnoreCase(person)){
                return true;
            }
        }
        return false;
    }

}