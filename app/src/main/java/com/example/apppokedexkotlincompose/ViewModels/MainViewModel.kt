package com.example.apppokedexkotlincompose.ViewModels

import android.hardware.biometrics.BiometricManager.Strings
import android.util.Log
import androidx.compose.runtime.saveable.Saver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppokedexkotlincompose.Data.AllPokemons.AllPokemons
import com.example.apppokedexkotlincompose.Data.Facts.Facts
import com.example.apppokedexkotlincompose.Data.Pokemon.Pokemon
import com.example.apppokedexkotlincompose.Data.SimplePokemon.PokemonSimple
import com.example.apppokedexkotlincompose.Retrofit.RetrofitServiceFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

    private val _allPokemon = MutableStateFlow<List<String>?>(null)
    val allPokemons: StateFlow<List<String>?> get() = _allPokemon


    private val _likePokemon = MutableStateFlow<List<String>?>(null)
    val likePokemons: StateFlow<List<String>?> get() = _likePokemon

    private val _listofPokemon = MutableStateFlow<List<PokemonSimple>?>(null)
    val listofPokemon: StateFlow<List<PokemonSimple>?> get() = _listofPokemon


    fun setAllpokemons() {
        viewModelScope.launch {
            try {
                val result = service.pokemons(0, 9999)
                val namesList = result.results.map { it.name }
                _allPokemon.value = namesList
                Log.w("",_allPokemon.value.toString())

                setpokemonsLike("")

                Log.w("MainViewModel", "Hola" + _allPokemon.value.toString())
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error at setting allpokemons", e)
            }
        }
    }

    private suspend fun transformNamestoPoke() {
        val namesToFetch = _likePokemon.value
        try {
            val filteredPokemon = coroutineScope {
                namesToFetch?.map { name ->
                    async { service.pokemonSimple(name) }
                }?.awaitAll() ?: emptyList()
            }
            _listofPokemon.value = filteredPokemon
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error at transformNamestoPoke: ${e.message}", e)
        }
    }

    fun setpokemonsLike(like: String){
        viewModelScope.launch {
            try {
                Log.w("pre", _allPokemon.value.toString())
                val namesLike = _allPokemon.value?.filter { it.startsWith(like, ignoreCase = true) }
                if (namesLike != null) {
                    _likePokemon.value = namesLike.take(20)
                }else{
                    Log.w("HAS TROBAT ON FALLA", "ANEM BE")
                }
                transformNamestoPoke()
                Log.w("MainViewModel","Hola estas" + _likePokemon.value.toString())
            } catch (e: Exception){
                Log.e("MainViewModel","Error at filtering", e)
            }
        }
    }


    fun setpokemon(nomPokemon: String){
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
        viewModelScope.launch {
            try {
                val result = service.facts(id)
                _facts.value = result
                Log.w("MainViewModel","Hola" + result.toString())
            } catch (e: Exception){
                Log.e("MainViewModel","Error at setting facts", e)
            }
        }
    }

}