package com.example.prophysiowearapp

import android.os.Bundle
import androidx.compose.material.Surface
import androidx.wear.compose.material.MaterialTheme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController


import com.example.prophysiowearapp.navigation.AppNavHost
import com.example.prophysiowearapp.ui.theme.ProPhysioWearAppTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    ProPhysioWearAppTheme {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AppNavHost(navController = navController)
        }
    }
}
