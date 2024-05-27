package com.fabiolourenco.catbreedsapp.ui.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fabiolourenco.catbreedsapp.ui.feature.breeds.Breeds
import com.fabiolourenco.catbreedsapp.ui.feature.favorites.Favorites

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val titleId = when (navBackStackEntry.value?.destination?.route) {
                Route.Breeds.route -> Route.Breeds.titleId
                Route.Favorites.route -> Route.Favorites.titleId
                else -> -1
            }
            if (titleId > -1) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = titleId))
                    }
                )
            }
        },
        bottomBar = {
            val items = listOf(
                Route.Breeds,
                Route.Favorites
            )
            NavigationBar {
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.labelId).uppercase()
                            )
                        },
                        icon = {},
                        alwaysShowLabel = true,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Breeds.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = Route.Breeds.route) {
                Breeds(
                    viewModel = hiltViewModel(),
                    goToBreedsDetails = { breed ->
                    }
                )
            }
            composable(route = Route.Favorites.route) {
                Favorites(
                    viewModel = hiltViewModel(),
                    goToBreedsDetails = { breed ->
                    }
                )
            }
        }
    }
}