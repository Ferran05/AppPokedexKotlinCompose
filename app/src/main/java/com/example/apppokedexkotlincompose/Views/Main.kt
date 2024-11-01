package com.example.apppokedexkotlincompose.Views

import androidx.compose.foundation.layout.Column
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

@Composable
fun Main(viewModel: MainViewModel){

    var pokemon by remember { mutableStateOf(TextFieldValue(" ")) }
    //val pokemon by viewModel.pokemon.collectAsState()
    Column {
        TextField(
            value = pokemon, onValueChange = { pokemon = it },
            placeholder = { Text("Buscador") },
            modifier = Modifier
            .width(300.dp)
            .padding(start = 10.dp, end = 5.dp)
            .padding(top = 20.dp)
        )

        Button(
            onClick = {viewModel.setpokemon("ditto")}
        )
        {
            Text("hola")
        }
    }
}