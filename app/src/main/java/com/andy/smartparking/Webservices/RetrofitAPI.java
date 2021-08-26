package com.andy.smartparking.Webservices;

import com.andy.smartparking.DirectionsModel.DirectionResponseModel;
import com.andy.smartparking.Model.GoogleResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    @GET
    Call<DirectionResponseModel> getDirection(@Url String url);
}
