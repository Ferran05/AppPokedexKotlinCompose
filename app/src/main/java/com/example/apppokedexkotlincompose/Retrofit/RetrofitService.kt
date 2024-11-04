package com.example.apppokedexkotlincompose.Retrofit

import androidx.compose.ui.geometry.Offset
import com.example.apppokedexkotlincompose.Data.AllPokemons.AllPokemons
import com.example.apppokedexkotlincompose.Data.Facts.Facts
import com.example.apppokedexkotlincompose.Data.Pokemon.Pokemon
import com.example.apppokedexkotlincompose.Data.SimplePokemon.PokemonSimple
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("pokemon/{name}")
    suspend fun pokemon(@Path("name") name: String): Pokemon

    @GET("pokemon-species/{id}")
    suspend fun facts(@Path("id") id: Int): Facts

    @GET("pokemon/")
    suspend fun pokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): AllPokemons

    @GET("pokemon/{name}")
    suspend fun pokemonSimple(@Path("name") name: String): PokemonSimple
}

object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}