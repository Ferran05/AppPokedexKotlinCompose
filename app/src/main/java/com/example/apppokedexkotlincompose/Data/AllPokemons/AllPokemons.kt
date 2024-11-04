package com.example.apppokedexkotlincompose.Data.AllPokemons

data class AllPokemons(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<Result>
)