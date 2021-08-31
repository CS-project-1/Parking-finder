package com.andy.smartparking.Webservices;

import com.andy.smartparking.AccessToken;
import com.andy.smartparking.STKPush;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class STKPushService {
    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPush> sendPush(@Body STKPush stkPush) {
        return null;
    }

    @GET("oauth/v1/generate?grant_type=client_credentials")
    public Call<AccessToken> getAccessToken() {
        return null;
    }
}
