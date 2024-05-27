package com.fabiolourenco.catbreedsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabiolourenco.catbreedsapp.ui.navigation.NavGraph
import com.fabiolourenco.catbreedsapp.ui.theme.CatBreedsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatBreedsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatBreedsAppTheme {
                NavGraph(
                    breedsViewModel = hiltViewModel(),
                    favoritesViewModel = hiltViewModel()
                )
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    MaterialTheme.colorScheme.background,
                    darkIcons = !isSystemInDarkTheme()
                )
            }
        }
    }
}