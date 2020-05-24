package com.example.progmobile3a;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {

    @GET("/api/v2/pokemon")
    Call<RestPokeResponse> getPokeResponse();
}
