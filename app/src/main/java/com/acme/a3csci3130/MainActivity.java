package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class handle the main activities of the application. It initialize the firebase database
 * and provide a list view to display created business. It also create onClick listener to
 * identify the business need to perform operation.
 */

public class MainActivity extends Activity {


    private ListView contactListView;
    private FirebaseListAdapter<Business> firebaseAdapter;

    /**
     * This is the onCreate method which initialize the firebase database and handle the clicked
     * business.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("business");

        //Get the reference to the UI contents
        contactListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
       firebaseAdapter = new FirebaseListAdapter<Business>(this, Business.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Business model, int position) {
                TextView BusinessNum = (TextView)v.findViewById(android.R.id.text1);
                BusinessNum.setText(model.businessNum);
            }
        };
        contactListView.setAdapter(firebaseAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Business business = (Business) firebaseAdapter.getItem(position);
                showDetailView(business);
            }
        });
    }

    /**
     * This method reflect button click which create an intent to jump to create business activity.
     * @param v is the current view
     */
    public void createContactButton(View v)
    {
        Intent intent=new Intent(this, CreateBusinessAcitivity.class);
        startActivity(intent);
    }

    /**
     * This method store the user-clicked Business into intent and jump to detail view activity
     * @param business
     */
    private void showDetailView(Business business)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Business", business);
        startActivity(intent);
    }



}
