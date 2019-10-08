package com.example.pokedexapp.api

import com.example.pokedexapp.data.PokemonListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun getPokemons(): Call<PokemonListResult>

    @GET("pokemon")
    fun getPokemons(@Query("offset") offset: Int, @Query("limit") limit: Int = 20): Call<PokemonListResult>
}