package com.example.pokedexapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedexapp.R
import com.example.pokedexapp.data.Pokemon
import com.example.pokedexapp.data.PokemonListResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokelist_item.view.*

class PokemonListAdapter(private val pokeList: PokemonListResult): RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokelist_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokeList.results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindPokemon(pokeList.results[position])
    }


    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bindPokemon(pokemon: Pokemon) {
            with(pokemon) {
                itemView.pokemonNumberTextView.text = pokemon.id.toString().orEmpty()
                itemView.pokemonNameTextView.text = pokemon.name.orEmpty()
                Picasso.get().load(pokemon.imageUrl).into(itemView.pokemonImageView)
            }
        }
    }
}