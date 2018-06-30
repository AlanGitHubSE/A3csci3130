package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * This class provide handler for create a new business instance, which let
 * user to fill or select their business details
 */

public class CreateBusinessAcitivity extends Activity {

    private Button submitButton;
    private EditText businessNumField, nameField, addressField;
    private MyApplicationData appState;
    private Spinner spinner;
    private Spinner spinner2;
    private String primarySelect;
    private String provinceSelect;

    /**
     * This is the onCreate method which map and initialize all fields that
     * used by create_business_activities. It also set up and handle the spinner to provide
     * drop-down list for user chose their primary business and province/territory.
     *
     * @param savedInstanceState is the current instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_business_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        businessNumField = (EditText) findViewById(R.id.businessNum);
        nameField = (EditText) findViewById(R.id.Name);
        addressField = (EditText) findViewById(R.id.address_field);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        String[] primaryBusiness = new String[]{"Please select your primary Business", "Fisher", "Distributor", "Processor", "Fish Monger"};
        String[] provinceList = new String[]{"Please select your province/territory", "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"};

        ArrayAdapter<String> businessAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, primaryBusiness);
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, provinceList);
        spinner.setAdapter(businessAdapter);
        spinner2.setAdapter(provinceAdapter);

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
                    primarySelect = "";
            }

            /**
             * THis method is used when there the selected item become not available in the spinner
             * @param adapterView is the view of the spinner
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                primarySelect = "";
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
                    provinceSelect = "";

            }

            /**
             * THis method is used when there the selected item become not available in the spinner
             * @param adapterView is the view of the spinner
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                provinceSelect = "";
            }
        });
    }

    /**
     * This method retrieve and store all the business detail from view
     *
     * @param v is the view of current corresponding activities.
     */
    public void submitInfoButton(View v) {
        //each entry needs a unique ID

        String businessID = appState.firebaseReference.push().getKey();
        String business = businessNumField.getText().toString();
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();

        Business newBusiness = new Business(businessID, business, name, primarySelect, address, provinceSelect);
        appState.firebaseReference.child(businessID).setValue(newBusiness);

        finish();

    }
}
