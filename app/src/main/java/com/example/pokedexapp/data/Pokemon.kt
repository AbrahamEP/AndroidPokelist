package com.example.pokedexapp.data

data class Pokemon(val name: String, val url: String) {
    val id: String
        get() {
            var separatedUrl = url.split("/")
            separatedUrl = separatedUrl.dropLast(1)
            return separatedUrl.last()
        }

    val imageUrl: String
        get() {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        }
}

data class PokemonListResult(val results: List<Pokemon>)