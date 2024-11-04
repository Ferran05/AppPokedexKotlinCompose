package com.example.apppokedexkotlincompose.Views

import android.graphics.Color.alpha
import android.util.Log
import android.util.MonthDisplayHelper
import android.widget.Space
import androidx.annotation.ColorRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.apppokedexkotlincompose.Data.Facts.Facts
import com.example.apppokedexkotlincompose.Data.Pokemon.Pokemon
import com.example.apppokedexkotlincompose.ViewModels.MainViewModel
import com.example.apppokedexkotlincompose.ui.theme.AppPokedexKotlinComposeTheme
import com.example.apppokedexkotlincompose.ui.theme.Purple40
import com.example.apppokedexkotlincompose.ui.theme.bug
import com.example.apppokedexkotlincompose.ui.theme.dark
import com.example.apppokedexkotlincompose.ui.theme.dragon
import com.example.apppokedexkotlincompose.ui.theme.electric
import com.example.apppokedexkotlincompose.ui.theme.fairy
import com.example.apppokedexkotlincompose.ui.theme.fighting
import com.example.apppokedexkotlincompose.ui.theme.fire
import com.example.apppokedexkotlincompose.ui.theme.flying
import com.example.apppokedexkotlincompose.ui.theme.ghost
import com.example.apppokedexkotlincompose.ui.theme.grass
import com.example.apppokedexkotlincompose.ui.theme.ground
import com.example.apppokedexkotlincompose.ui.theme.ice
import com.example.apppokedexkotlincompose.ui.theme.normal
import com.example.apppokedexkotlincompose.ui.theme.poison
import com.example.apppokedexkotlincompose.ui.theme.psychic
import com.example.apppokedexkotlincompose.ui.theme.rock
import com.example.apppokedexkotlincompose.ui.theme.steel
import com.example.apppokedexkotlincompose.ui.theme.water
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


fun extractNumber(url: String): String? {
    val regex = Regex("\\d+(?=[^\\d]*$)")
    return regex.find(url)?.value?.toString()
}

fun capFirstLetter(input: String): String {
    return input.replaceFirstChar { char ->
        if (char.isLowerCase()) char.uppercaseChar() else char
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Infomain (viewModel: MainViewModel, namePokemon: String, navController: NavController) {
    val pokemonState: State<Pokemon?> = viewModel.pokemon.collectAsState(initial = null)
    val pokemon = pokemonState.value

    val factsState: State<Facts?> = viewModel.facts.collectAsState(initial = null)
    val facts = factsState.value

    val gridState = rememberLazyGridState()

    LaunchedEffect(namePokemon) {
        viewModel.setpokemon(namePokemon)
    }

    val pokemonTypeColors = mapOf(
        "bug" to bug,
        "dark" to dark,
        "dragon" to dragon,
        "electric" to electric,
        "fairy" to fairy,
        "fighting" to fighting,
        "fire" to fire,
        "flying" to flying,
        "ghost" to ghost,
        "grass" to grass,
        "ground" to ground,
        "ice" to ice,
        "normal" to normal,
        "poison" to poison,
        "psychic" to psychic,
        "rock" to rock,
        "steel" to steel,
        "water" to water
    )

    fun PokemonTypeBackground(type: String): Color {
        Log.e("color",type)
        return pokemonTypeColors[type] ?: Color.Gray //Gray is defaults
    }

    if(pokemon != null && facts != null) {
        val backgroundColor = PokemonTypeBackground(pokemon.types[0].type.name)

        Box(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.copy(alpha = 0.6f)), contentAlignment = Alignment.TopCenter) {
            Column (modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 8.dp)){
                Button(onClick = {navController.navigate("main")}) { Text("Tornar")}
                Card (modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray) , horizontalArrangement = Arrangement.SpaceBetween){
                        Text(capFirstLetter(pokemon.name),fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(start = 5.dp))
                        Row {
                            for (i in pokemon.stats){
                                if (i.stat.name == "hp"){
                                    Text(i.base_stat.toString() + "HP", fontWeight = FontWeight.Bold,color = Color.DarkGray, modifier = Modifier.padding(end = 10.dp))
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
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){

                        Text((pokemon.weight.toFloat() / 10).toString() + " kg")
                        Text((pokemon.height.toFloat() / 10).toString() + " m")
                    }


                }
                Text("Descripción", fontSize = 20.sp)
                val pagerState = rememberPagerState()
                val spanishEntries = facts.flavor_text_entries.filter { it.language.name == "es" }
                HorizontalPager (
                    modifier = Modifier.fillMaxWidth(),
                    state = pagerState,
                    count = spanishEntries.size) { page ->
                    Card(modifier = Modifier.padding(5.dp)) {
                        Text(
                            text = spanishEntries[page].flavor_text.replace("\n", " "),
                            modifier = Modifier
                                .height(120.dp)
                                .padding(start = 5.dp),
                            textAlign = TextAlign.Justify
                        )
                    }
                }


                Text("Stats", fontSize = 20.sp)

            }

        }
    }

}

