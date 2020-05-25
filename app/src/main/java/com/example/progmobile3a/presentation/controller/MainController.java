package com.example.progmobile3a.presentation.controller;

import android.content.SharedPreferences;

import com.example.progmobile3a.Constants;
import com.example.progmobile3a.Singletons;
import com.example.progmobile3a.presentation.model.Pokemon;
import com.example.progmobile3a.presentation.model.RestPokeResponse;
import com.example.progmobile3a.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

        List<Pokemon> pokemonList = getDataFromCache();

        if(pokemonList != null){
            view.showList(pokemonList);
        }else {
            makeApiCall();
        }
    }

    private void makeApiCall(){

        Call<RestPokeResponse> call = Singletons.getPokeApi().getPokeResponse();
        call.enqueue(new Callback<RestPokeResponse>() {
            @Override
            public void onResponse(Call<RestPokeResponse> call, Response<RestPokeResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Pokemon> pokemonList = response.body().getResults();
                    saveList(pokemonList);
                    view.showList(pokemonList);
                }else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokeResponse> call, Throwable t) {
                view.showError();
            }
        });

    }

    private void saveList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);

        sharedPreferences
                .edit()
                .putString("jsonPokemonList", jsonString)
                .apply();
    }

    private List<Pokemon> getDataFromCache() {

        String jsonPokemon = sharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);

        if(jsonPokemon == null){
            return null;
        }else {
            Type listType = new TypeToken<List<Pokemon>>() {
            }.getType();
            return gson.fromJson(jsonPokemon, listType);
        }

    }

    public void onItemClick(Pokemon pokemon){

    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}
