package com.example.apppokedexkotlincompose.Data.SimplePokemon

import com.google.gson.annotations.SerializedName

data class OtherSimple(
    @SerializedName("official-artwork")
    val official_artwork: OfficialArtworkSimple,
)