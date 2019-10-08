package com.example.pokedexapp.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.pokedexapp.R
import com.example.pokedexapp.api.PokemonAPI
import com.example.pokedexapp.data.Pokemon
import com.example.pokedexapp.data.PokemonListResult
import com.example.pokedexapp.ui.adapters.PokemonListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val pokemonApi = PokemonAPI()
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var adapter: PokemonListAdapter
    private lateinit var pokeList: List<Pokemon>
    private val getPokemonsCallback = object: Callback<PokemonListResult> {
        override fun onFailure(call: Call<PokemonListResult>, t: Throwable) {
            Log.e("MainActivity", "Problem calling Pokemon API", t)
        }

        override fun onResponse(call: Call<PokemonListResult>, response: Response<PokemonListResult>) {
            val results = response.body()?.results ?: emptyList()
            val pokeListResult = PokemonListResult(results)
            adapter.addPokemons(pokeListResult)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeList = emptyList()
        adapter = PokemonListAdapter(PokemonListResult(pokeList))
        linearLayout = LinearLayoutManager(this)
        pokelistRecycler.adapter = adapter

        setRecyclerViewScrollListener()
        refreshButton.setOnClickListener {
            pokemonApi.getPokemons(getPokemonsCallback)
        }

        pokelistRecycler.layoutManager = linearLayout
        if (isNetworkConnected()) {
            pokemonApi.getPokemons(getPokemonsCallback)
        }else{
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) {_,_ ->}
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun setRecyclerViewScrollListener() {
        pokelistRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = pokelistRecycler.layoutManager!!.itemCount
                if (itemCount == linearLayout.findLastVisibleItemPosition() + 1) {
                    //Load more data
                    pokemonApi.getPokemons(itemCount, 20, this@MainActivity.getPokemonsCallback)
                }
            }
        })
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
