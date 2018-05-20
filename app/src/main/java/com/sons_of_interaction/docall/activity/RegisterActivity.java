package com.sons_of_interaction.docall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sons_of_interaction.docall.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etFiscalCode,etEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Registrazione");

        etFiscalCode=(EditText) findViewById(R.id.cf_utente);
        etEmail=(EditText) findViewById(R.id.email_utente);
        Button btnSignIn = (Button)findViewById(R.id.btn_register);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(isFiscalCodeValid(etFiscalCode.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Email inviata all'indirizzo indicato", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(registerIntent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Codice Fiscale non valido", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean isFiscalCodeValid(String fiscalCode) {
        return fiscalCode.length()==16;
    }
}