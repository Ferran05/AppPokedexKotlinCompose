package com.example.apppokedexkotlincompose.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppokedexkotlincompose.ViewModels.MainViewModel
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun Main(viewModel: MainViewModel, navController: NavController){

    var pokemon by remember { mutableStateOf(TextFieldValue("")) }
    //val pokemon by viewModel.pokemon.collectAsState()
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(top = 50.dp)) {
        TextField(
            value = pokemon, onValueChange = { pokemon = it },
            placeholder = { Text("Buscador") },
            modifier = Modifier
            .width(300.dp)
            .padding(start = 10.dp, end = 5.dp)
            .padding(top = 20.dp)
        )

        Button(
            onClick = {
                viewModel.setpokemon(pokemon.text)
                navController.navigate("info/${pokemon.text}")
            },
            modifier = Modifier.padding(top = 25.dp)
        )
        {
            Text("Buscar")
        }
    }
}