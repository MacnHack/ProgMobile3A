package com.example.progmobile3a.data;

import com.example.progmobile3a.presentation.model.RestPokeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {

    @GET("/api/v2/pokemon")
    Call<RestPokeResponse> getPokeResponse();
}
