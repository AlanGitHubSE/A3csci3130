package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * This class display selected business information from database
 * and also handle information that edit by user and re-store it to database
 */

public class DetailViewActivity extends Activity {

    private EditText businessNumField, nameField, addressField;
    Business existBusiness;
    private Spinner spinner;
    private Spinner spinner2;
    private String primarySelect;
    private String provinceSelect;
    private MyApplicationData appState;

    /**
     * This is the onCreate method which retrieve all the information of a particular business
     * and display to user to edit.
     *
     * @param savedInstanceState is the current instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        existBusiness = (Business) getIntent().getSerializableExtra("Business");
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        businessNumField = (EditText) findViewById(R.id.businessNum2);
        nameField = (EditText) findViewById(R.id.Name2);
        addressField = (EditText) findViewById(R.id.address_field2);
        spinner = (Spinner) findViewById(R.id.spinner3);
        spinner2 = (Spinner) findViewById(R.id.spinner4);
        String[] primaryBusiness = new String[]{"Please select your primary Business", "Fisher", "Distributor", "Processor", "Fish Monger"};
        String[] provinceList = new String[]{"Please select your province/territory", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"};
        ArrayAdapter<String> businessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, primaryBusiness);
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinceList);
        spinner.setAdapter(businessAdapter);
        spinner2.setAdapter(provinceAdapter);

        if (existBusiness != null) {
            businessNumField.setText(existBusiness.businessNum);
            nameField.setText(existBusiness.name);
            addressField.setText(existBusiness.address);

            int position1 = businessAdapter.getPosition(existBusiness.primaryBusiness);
            int position2 = provinceAdapter.getPosition(existBusiness.province);
            if (position1 != 0)
                spinner.setSelection(position1);
            if (position2 != 0)
                spinner2.setSelection(position2);
        }

        //find what user selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * This method used to response when there is an item being selected in spinner
             * @param adapterView is the current view
             * @param view is the view been clicked within adapterView
             * @param i is the number of the view being selected
             * @param l is the row id of the item
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                primarySelect = adapterView.getItemAtPosition(i).toString();
                if (primarySelect.equals("Please select your primary Business"))
                    primarySelect = null;
            }

            /**
             * THis method is used when there the selected item become not available in the spinner
             * @param adapterView is the view of the spinner
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                primarySelect = null;
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * This method used to response when there is an item being selected in spinner
             * @param adapterView is the current view
             * @param view is the view been clicked within adapterView
             * @param i is the number of the view being selected
             * @param l is the row id of the item
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provinceSelect = adapterView.getItemAtPosition(i).toString();
                if (provinceSelect.equals("Please select your province/territory"))
                    provinceSelect = null;
            }

            /**
             * THis method is used when there the selected item become not available in the spinner
             * @param adapterView is the view of the spinner
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                provinceSelect = null;
            }
        });
    }

    /**
     * This method retrieved all the information that user edit from view and upload it to database
     *
     * @param v is the current view
     */
    public void updateBusiness(View v) {

        String business = businessNumField.getText().toString();
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();

        Business updateBusiness = new Business(existBusiness.businessID, business, name,
                primarySelect, address, provinceSelect);
        appState.firebaseReference.child(existBusiness.businessID).setValue(updateBusiness);
        finish();
    }

    /**
     * This method delete current selected Business from database and return to main activities.
     *
     * @param v is the current view
     */
    public void eraseContact(View v) {
        appState.firebaseReference.child(existBusiness.businessID).removeValue();
        finish();
    }
}
