package com.example.apppokedexkotlincompose.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppokedexkotlincompose.Data.Facts.Facts
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

    private val _facts = MutableStateFlow<Facts?>(null)
    val facts: StateFlow<Facts?> get() = _facts //Getter

    fun setpokemon(nomPokemon: String){
        Log.w("MainViewModel","Hola" + nomPokemon)
        viewModelScope.launch {
            try {
                val result = service.pokemon(nomPokemon)
                _pokemon.value = result
                pokemon.value?.let { setfacts(it.id) }
                Log.w("MainViewModel","Hola" + result.toString())
            } catch (e: Exception){
                Log.e("MainViewModel","Error at setting pokemon", e)
            }
        }
    }


    fun setfacts(id: Int){
        Log.w("MainViewModel","Hola" + id)
        viewModelScope.launch {
            try {
                val result = service.facts(id)
                _facts.value = result
                Log.w("MainViewModel","Hola" + result.toString())
            } catch (e: Exception){
                Log.e("MainViewModel","Error at setting pokemon", e)
            }
        }
    }
}