package com.andy.smartparking;

public interface SavedParkingInterface {
    void onLocationClick(SavedPlaceModel savedPlaceModel);

    void onLocationClick(SavedPlaceModel savedPlaceModel, GooglePlaceModel googlePlaceModel);
}
