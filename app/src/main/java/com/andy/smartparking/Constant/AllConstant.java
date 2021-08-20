package com.andy.smartparking.Constant;

import com.andy.smartparking.Model.ParkingLocation;
import com.andy.smartparking.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AllConstant {

    public static int STORAGE_REQUEST_CODE = 1000;
    public static int LOCATION_REQUEST_CODE = 2000;
    public static final String IMAGE_PATH = "/Profile/image_profile.jpg";





    //    String IMAGE_PATH = "/Profile/image_profile.jpg";


    public static ArrayList<ParkingLocation> placesName = new ArrayList<>(
            Arrays.asList(
                    new ParkingLocation(1, R.drawable.ic_parking, "parking", "parking")

            )
    );
}
