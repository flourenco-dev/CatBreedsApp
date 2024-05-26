package com.fabiolourenco.catbreedsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import com.fabiolourenco.catbreedsapp.ui.feature.breeds.Breeds
import com.fabiolourenco.catbreedsapp.ui.theme.CatBreedsAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatBreedsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatBreedsAppTheme {
                Breeds()
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    MaterialTheme.colorScheme.background,
                    darkIcons = !isSystemInDarkTheme()
                )
            }
        }
    }
}