package com.fabiolourenco.catbreedsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fabiolourenco.catbreedsapp.ui.feature.breeds.Breeds
import com.fabiolourenco.catbreedsapp.ui.theme.CatBreedsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatBreedsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatBreedsAppTheme {
                Breeds()
            }
        }
    }
}