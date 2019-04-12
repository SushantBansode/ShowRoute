package com.example.sushant_bansde;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface
{
    @GET("jsonTest")
    Call<GetLatLongResponse> getLatAndLong();
}
