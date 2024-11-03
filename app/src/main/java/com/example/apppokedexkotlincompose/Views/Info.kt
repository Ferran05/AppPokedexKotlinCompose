package com.example.apppokedexkotlincompose.Views

import android.util.Log
import android.util.MonthDisplayHelper
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apppokedexkotlincompose.Data.Facts.Facts
import com.example.apppokedexkotlincompose.Data.Pokemon.Pokemon
import com.example.apppokedexkotlincompose.ViewModels.MainViewModel


fun extractNumber(url: String): String? {
    val regex = Regex("\\d+(?=[^\\d]*$)")
    return regex.find(url)?.value?.toString()
}

fun capFirstLetter(input: String): String {
    return input.replaceFirstChar { char ->
        if (char.isLowerCase()) char.uppercaseChar() else char
    }
}

@Composable
fun Infomain (viewModel: MainViewModel, namePokemon: String, navController: NavController) {
    val pokemonState: State<Pokemon?> = viewModel.pokemon.collectAsState(initial = null)
    val pokemon = pokemonState.value

    val factsState: State<Facts?> = viewModel.facts.collectAsState(initial = null)
    val facts = factsState.value

    LaunchedEffect(namePokemon) {
        viewModel.setpokemon(namePokemon)
    }
    if(pokemon != null && facts != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column (modifier = Modifier.padding(top = 20.dp)){
                Card (modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray) , horizontalArrangement = Arrangement.SpaceBetween){
                        Text(capFirstLetter(pokemon.name),fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(start = 5.dp))
                        Row {
                            for (i in pokemon.stats){
                                if (i.stat.name == "hp"){
                                    Text(i.base_stat.toString() + "HP", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 10.dp))
                                }
                            }
                            for (i in pokemon.types) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(
                                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/${
                                                extractNumber(
                                                    i.type.url
                                                )
                                            }.png"
                                        )
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Image of the pokemon",
                                    modifier = Modifier.width(100.dp)
                                )
                            }
                        }
                    }

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(pokemon.sprites.other.official_artwork.front_default)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Image of the pokemon",
                            modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
                        )
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){

                        Text(pokemon.weight.toString() + " kg")
                        Text((pokemon.height.toFloat() / 10).toString() + " m")
                    }


                }
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item{
                        for (i in facts.flavor_text_entries){
                            if (i.language.name == "es"){
                                    Text(i.flavor_text , modifier = Modifier.padding(top = 20.dp))

                            }
                        }
                    }
                }

            }

        }
    }

}