package com.example.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/*
Doktornak jelenik meg 2 gomb: appointment létrehozása, saját appointmentek
létrehozásnál egy form jelenik meg, ki lehet tölteni és feltölteni az új időpontot.
saját időpontoknál kilistázódnak az orvoshoz tartozó időpontok, ezeken van egy szerkesztés
és törlés gomb, a szerkesztésnél átdob egy formra (mint a létrehozás form) ahol be vannak töltve az
adatok és szerkeszteni lehet őket.
 */

public class DoctorActivity extends AppCompatActivity {
    private static final String LOG_TAG = DoctorActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<Appointment> mItemList;
    private DoctorAppointmentAdapter mAdapter;

    private int gridNumber = 1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_doctor);
        mAuth = FirebaseAuth.getInstance();
        // mAuth.signOut();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();

        mAdapter = new DoctorAppointmentAdapter(this, mItemList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Appointments");
        queryData();
    }

    private void queryData() {
        mItemList.clear();

        mItems.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Appointment item = document.toObject(Appointment.class);
                mItemList.add(item);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

//        getMenuInflater().inflate(R.menu.doctor_appointment_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.new_appointment);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_appointment:
                return true;
            case R.id.logout_button:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {



        return super.onPrepareOptionsMenu(menu);
    }


    public void editAppointment(String id) {
        
    }

    public void deleteAppointment(String id) {
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.signOut();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
        Log.i(LOG_TAG, "onDestroy");
    }
}