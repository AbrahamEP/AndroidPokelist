package com.example.pokedexapp.api

import com.example.pokedexapp.data.PokemonListResult
import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {
    @GET("/api/v2/pokemon")
    fun getPokemons(): Call<PokemonListResult>
}