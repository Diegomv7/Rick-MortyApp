package com.example.rickmortyapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickmortyapp.models.Location
import com.example.rickmortyapp.models.Origin
import com.example.rickmortyapp.models.Result
import com.example.rickmortyapp.services.CharacterService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DetailScreen(id: Int, innerPadding: PaddingValues, navController: NavController) {
    var character by remember {
        mutableStateOf(
            Result(
                created= "",
                episode= listOf(),
                gender= "",
                id= 0,
                image= "",
                location= Location(name = "", url = ""),
                name= "",
                origin= Origin(name = "", url = ""),
                species= "",
                status= "",
                type= "",
                url= ""
            )
        )
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            val BASE_URL = "https://rickandmortyapi.com/api/"
            val characterService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CharacterService::class.java)
            val response = characterService.getCharacter(id)
            isLoading = false
            character= response
        }
    }
    if (isLoading) {
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagen y nombre del personaje
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = character.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color(0x88000000))
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información detallada del personaje
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoRow(label = "Estado", value = character.status)
                InfoRow(label = "Especie", value = character.species)
                InfoRow(label = "Género", value = character.gender)
                InfoRow(label = "Origen", value = character.origin.name)
                InfoRow(label = "Ubicación", value = character.location.name)
                InfoRow(label = "Número de Episodios", value = character.episode.size.toString())
            }

            // Botón para regresar al HomeScreen
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Volver", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}


