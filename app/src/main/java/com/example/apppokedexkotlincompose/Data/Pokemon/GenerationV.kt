package com.example.apppokedexkotlincompose.Data.Pokemon

import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val black_white: BlackWhite
)