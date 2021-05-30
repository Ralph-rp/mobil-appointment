package com.example.appointment;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RegisterService extends AsyncTask<Void, Void, Actor> {
    private static final String LOG_TAG = RegisterActivity.class.getName();

    private Actor actor;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    public RegisterService(Actor actor) {
    this.actor = actor;
    }

    public RegisterService() {

    }

    protected Actor doInBackground(Void... voids) {
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Users");
        mItems.add(actor);
        return this.actor;
    }

    public boolean deleteUser(Actor actor) {
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Users");

        return true;
    }

    public boolean updateUser(Actor actor) {
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Users");

        return true;
    }

    public ArrayList<Actor> getAll() {
        ArrayList<Actor> mItemList = new ArrayList<>();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Actor item = document.toObject(Actor.class);
                                mItemList.add(item);
                            }
                        } else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return mItemList;
    }

}
