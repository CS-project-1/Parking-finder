package com.andy.smartparking.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//class returns location of a place

public class GeometryModel {
    @SerializedName("location")
    @Expose
    private LocationModel location;

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }


}
