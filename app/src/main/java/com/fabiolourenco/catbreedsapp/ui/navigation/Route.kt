package com.fabiolourenco.catbreedsapp.ui.navigation

import androidx.annotation.StringRes
import com.fabiolourenco.catbreedsapp.R

sealed class Route(
    val route: String,
    @StringRes val titleId: Int,
    @StringRes val labelId: Int
) {
    data object Breeds : Route(
        route = "breeds",
        titleId = R.string.route_breeds_title,
        labelId = R.string.route_breeds_label
    )
    data object Favorites : Route(
        route = "favorites",
        titleId = R.string.route_favorites_title,
        labelId = R.string.route_favorites_label
    )
    data object Details : Route(
        route = "details/{breedId}",
        titleId = -1,
        labelId = -1
    ) {
        fun createRoute(breedId: String) = "details/$breedId"
    }
}