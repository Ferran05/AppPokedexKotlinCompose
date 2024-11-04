package com.example.apppokedexkotlincompose.Views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppokedexkotlincompose.ViewModels.MainViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apppokedexkotlincompose.Data.Pokemon.Pokemon
import com.example.apppokedexkotlincompose.Data.SimplePokemon.PokemonSimple

@Composable
fun Main(viewModel: MainViewModel, navController: NavController){

    var pokemon by remember { mutableStateOf(TextFieldValue("")) }
    val listPokemons by viewModel.listofPokemon.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.setAllpokemons()
        Log.d("MainScreen", "Exiting MainScreen")
        Log.w("AllPokemon", viewModel.allPokemons.toString())
        Log.w("LikePokemon", viewModel.likePokemons.toString())
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 50.dp)
        ) {
            TextField(
                value = pokemon, onValueChange = {
                    pokemon = it
                    viewModel.setpokemonsLike(it.text)
                },
                placeholder = { Text("Buscador") },
                modifier = Modifier
                    .width(300.dp)
                    .padding(start = 10.dp, end = 5.dp)
                    .padding(top = 20.dp)
            )
        }
        if (!listPokemons.isNullOrEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 180.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp), // Space between rows
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between columns
                ) {

                    items(listPokemons.orEmpty()) { pokemon ->
                        Column(){
                            CardPokemon(pokemon, navController)
                        }
                    }

                }

        }
    }
}
@Composable
fun CardPokemon(pokemon: PokemonSimple, navController: NavController){
    Card(onClick = {
        navController.navigate("info/${pokemon.name}")
    }) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.sprites.front_default)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image of the pokemon",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(pokemon.name)
        }
    }
}