package com.example.pokedexapp.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.pokedexapp.R
import com.example.pokedexapp.api.PokemonAPI
import com.example.pokedexapp.data.PokemonListResult
import com.example.pokedexapp.ui.adapters.PokemonListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val pokemonApi = PokemonAPI()
    private val getPokemonsCallback = object: Callback<PokemonListResult> {
        override fun onFailure(call: Call<PokemonListResult>, t: Throwable) {
            Log.e("MainActivity", "Problem calling Pokemon API", t)
        }

        override fun onResponse(call: Call<PokemonListResult>, response: Response<PokemonListResult>) {
            val pokeList = PokemonListResult(response.body()?.results ?: emptyList())
            pokelistRecycler.adapter = PokemonListAdapter(pokeList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshButton.setOnClickListener {
            pokemonApi.getPokemons(getPokemonsCallback)
        }

        pokelistRecycler.layoutManager = LinearLayoutManager(this)
        if (isNetworkConnected()) {
            pokemonApi.getPokemons(getPokemonsCallback)
        }else{
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) {_,_ ->}
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
