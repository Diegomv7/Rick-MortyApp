package com.example.rickmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickmortyapp.ui.screens.DetailScreen
import com.example.rickmortyapp.ui.screens.HomeScreen
import com.example.rickmortyapp.ui.theme.RickMortyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable("detail/{id}", arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                                nullable = false
                            }
                        )) {
                            val id = it.arguments?.getInt("id") ?:0
                            DetailScreen(innerPadding = innerPadding, id = id, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
