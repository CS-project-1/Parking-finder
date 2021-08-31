package com.andy.smartparking.Constant;

import com.andy.smartparking.Model.ParkingLocation;
import com.andy.smartparking.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AllConstant {

    public static int STORAGE_REQUEST_CODE = 1000;
    public static int LOCATION_REQUEST_CODE = 2000;
    public static final String IMAGE_PATH = "/Profile/image_profile.jpg";

    public static final int CONNECT_TIMEOUT = 60 * 1000;

    public static final int READ_TIMEOUT = 60 * 1000;

    public static final int WRITE_TIMEOUT = 60 * 1000;

    public static final String BASE_URL1 = "https://sandbox.safaricom.co.ke/";

    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String TRANSACTION_TYPE = "CustomerPayBillOnline";
    public static final String PARTYB = "174379"; //same as business shortcode above
    public static final String CALLBACKURL = "https://parking-finder-web-app.herokuapp.com";





    //    String IMAGE_PATH = "/Profile/image_profile.jpg";


    public static ArrayList<ParkingLocation> placesName = new ArrayList<>(
            Arrays.asList(
                    new ParkingLocation(1, R.drawable.ic_parking, "parking", "parking")

            )
    );
}
