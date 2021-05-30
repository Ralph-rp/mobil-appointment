package com.example.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/*
Pácienseknek jelennek meg az appointmentek listában
ezeket tudják lefoglalni/lemondani
 */
public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG = HomeActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<Appointment> mItemList;
    private AppointmentAdapter mAdapter;

    private int gridNumber = 1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
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

        mAdapter = new AppointmentAdapter(this, mItemList);
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
            if(mItemList.size() == 0) {
                initializeData();
                queryData();
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData() {
     //   String[] itemList;
        String[] id = getResources().getStringArray(R.array.appointment_ids);;
        String[] status = getResources().getStringArray(R.array.appointment_status);
        String[] appointmentType  = getResources().getStringArray(R.array.appointment_type);
        String[] description = getResources().getStringArray(R.array.appointment_description);
        String[] start = getResources().getStringArray(R.array.appointment_start);
        String[] end = getResources().getStringArray(R.array.appointment_end);
        String[] date = getResources().getStringArray(R.array.appointment_date);
        String[] slots = getResources().getStringArray(R.array.appointment_slots);
        TypedArray actors = getResources().obtainTypedArray(R.array.appointment_actors);

      //  mItemList.clear();

        for (int i = 0; i < id.length; i++) {
            mItems.add(new Appointment(
                    status[i], appointmentType[i],
                    "no reason", description[i],
                    start[i], end[i], date[i],
                    Integer.parseInt(slots[i])));
        }

        //egyeb tagot hozzaadasa setterekkel

       // mAdapter.notifyDataSetChanged();
    }

    public void bookAppointment(String id) {
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