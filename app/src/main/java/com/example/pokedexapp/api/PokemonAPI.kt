package com.example.pokedexapp.api

import com.example.pokedexapp.data.PokemonListResult
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonAPI {
    private val service: PokemonService

    companion object {
        const val BASE_URL = "https://pokeapi.co/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(PokemonService::class.java)
    }

    fun getPokemons(callback: Callback<PokemonListResult>) {
        val call = service.getPokemons()
        call.enqueue(callback)
    }
}