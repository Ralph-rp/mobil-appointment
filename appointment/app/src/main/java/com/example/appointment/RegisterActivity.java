package com.example.appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();

    EditText emailEditText;
    EditText pass1NameEditText;
    EditText pass2NameEditText;

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

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
//        String password = preferences.getString("password", null );


    }

    public void register(View view) {

        String emailStr = emailEditText.getText().toString();
        String passwordStr = pass1NameEditText.getText().toString();
        String password2Str = pass2NameEditText.getText().toString();

        if (!passwordStr.equals(password2Str)) {
            Log.e(LOG_TAG, "Nem egyenlő a két jelszó");
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");
                    startHomeScreen();
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

    public void cancel(View view) {
        finish();
    }
}