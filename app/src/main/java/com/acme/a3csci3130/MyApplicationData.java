package com.acme.a3csci3130;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Franz on 2017-05-31.
 * This class define and create the database and database reference for the project.
 */

public class MyApplicationData extends Application {

    //this initialize the firebase reference variable which points to the location of database
    public DatabaseReference firebaseReference;
    //this initialize the firebase instance variable to access the firebase database
    public FirebaseDatabase firebaseDBInstance;

}
