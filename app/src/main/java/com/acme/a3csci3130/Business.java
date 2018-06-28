package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse.
 */

public class Business implements Serializable {

    public String businessID;
    public String businessNum;
    public String name;
    public String primaryBusiness;
    public String address;
    public String province;

    /**
     * Constructor of the Business class, parameter includs:
     * @param businessID which is the unique identify number of database
     * @param businessNum is the user specified business number
     * @param name is the name of business owner
     * @param primaryBusiness is the type of business which includes Fisher, Distributor, Processor, Fish Monger
     * @param address is the address of the business
     * @param province is the location of the business, includs {AB, BC, MB, NB, NL, NS, NT, NU, ON, PE, QC, SK, YT, ""
     */
    public Business(String businessID, String businessNum, String name, String primaryBusiness, String address, String province) {
        this.businessID = businessID;
        this.businessNum = businessNum;
        this.name = name;
        this.primaryBusiness = primaryBusiness;
        this.address = address;
        this.province = province;
    }

    /**
     * Default constructor of Business class
     */
    public Business() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

}
