package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();

    EditText emailEditText;
    EditText pass1NameEditText;
    EditText pass2NameEditText;
    EditText nameEditText;
    EditText phoneNumberEditText;
    DatePicker birthDatePicker;
    Spinner genderSpinner;


    Spinner accountSpinner;

    private SharedPreferences preferences;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        pass1NameEditText = findViewById(R.id.passwordEditText);
        pass2NameEditText = findViewById(R.id.passwordAgainEditText);
        nameEditText = findViewById(R.id.nameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        birthDatePicker = findViewById(R.id.birthDateEditText);
        genderSpinner = findViewById(R.id.genderSpinner);

        accountSpinner = findViewById(R.id.accountTypeSpinner);


        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
//        String password = preferences.getString("password", null );

        genderSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        accountSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.accountTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);

        Log.i(LOG_TAG,"onCreate");
    }

    public void register(View view) {

        String emailStr = emailEditText.getText().toString();
        String passwordStr = pass1NameEditText.getText().toString();
        String password2Str = pass2NameEditText.getText().toString();
        String accountType = accountSpinner.getSelectedItem().toString();
        String name = nameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String birthDate = birthDatePicker.getDayOfMonth()+"/"+birthDatePicker.getMonth()+"/"+birthDatePicker.getYear();
        if (!passwordStr.equals(password2Str)) {
            Log.e(LOG_TAG, "Nem egyenlő a két jelszó");
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");

                  //  task.getResult().getUser()

                    Actor actor = new Actor(Objects.requireNonNull(task.getResult()).getUser().getUid(), emailStr,true, accountType, name, phoneNumber, gender, birthDate);
                    new RegisterService(actor).execute();
                    Log.e(LOG_TAG, actor.getType());
                    if(actor.getType().equals("Patient")) {
                        startHomeScreen();
                    } else {
                        startDoctorHomeScreen();
                    }


                } else {
                    Log.d(LOG_TAG, "User was't created successfully:", task.getException());
                    Toast.makeText(RegisterActivity.this, "User was't created successfully:", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void startDoctorHomeScreen() {
        Intent intent = new Intent(this, DoctorActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       String selectedItem = parent.getItemAtPosition(position).toString();

       Log.d(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }
}