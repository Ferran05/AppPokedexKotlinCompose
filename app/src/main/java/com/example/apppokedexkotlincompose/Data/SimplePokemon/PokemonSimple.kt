package com.example.apppokedexkotlincompose.Data.SimplePokemon

data class PokemonSimple(
    val id: Int,
    val name: String,
    val sprites: SpritesSimple,
    val types: List<TypeSimple>,
)