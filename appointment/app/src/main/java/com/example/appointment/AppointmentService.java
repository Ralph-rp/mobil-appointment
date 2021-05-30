package com.example.appointment;

import android.os.AsyncTask;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AppointmentService extends AsyncTask<Void, Void, Appointment> {

    private Appointment appointment;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;


    @Override
    protected Appointment doInBackground(Void... voids) {
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Appointments");
        mItems.add(appointment);
        return appointment;
    }

    public boolean updateAppointment(Appointment appointment) {
        mFirestore = FirebaseFirestore.getInstance();

        ArrayList<Appointment> mItemList = new ArrayList<>();



        return true;
    }

    public void deleteAppointment(Appointment appointment) {
        ArrayList<Appointment> mItemList = new ArrayList<>();

        for (Appointment item: mItemList
             ) {
            if(item.equals(appointment)) {
                mFirestore = FirebaseFirestore.getInstance();
                mFirestore.collection("Appointments").document(appointment.getId()).delete();
                return;
            }
        }

    }

    public ArrayList<Appointment> getAll() {
        ArrayList<Appointment> mItemList = new ArrayList<>();

        mItems.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Appointment item = document.toObject(Appointment.class);
                mItemList.add(item);
            }
        });

        return mItemList;
    }

}
