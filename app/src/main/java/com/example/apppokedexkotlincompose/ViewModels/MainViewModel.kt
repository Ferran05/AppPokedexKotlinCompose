package com.example.apppokedexkotlincompose.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedexkotlincompose.Data.Pokemon.Pokemon
import com.example.apppokedexkotlincompose.Retrofit.RetrofitServiceFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    //Retrofit Service
    private val service = RetrofitServiceFactory.makeRetrofitService()


    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    val pokemon: StateFlow<Pokemon?> get() = _pokemon //Getter
    fun setpokemon(nomPokemon: String){
        viewModelScope.launch {
            try {
                val result = service.pokemon(nomPokemon)
                _pokemon.value = result
                Log.w("MainViewModel","Hola" + result.toString())
            } catch (e: Exception){
                Log.e("MainViewModel","Error at setting pokemon", e)
            }
        }
    }
}