package com.fabiolourenco.ui.navigation

import androidx.annotation.StringRes
import com.fabiolourenco.common.R

sealed class Route(
    val route: String,
    @StringRes val titleId: Int = -1,
    @StringRes val labelId: Int = -1
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
        route = "details/{breedId}"
    ) {
        fun createRoute(breedId: String) = "details/$breedId"
    }
    data object PaginatedBreeds : Route(
        route = "paginatedBreeds",
        titleId = R.string.route_paginated_breeds_title,
        labelId = R.string.route_paginated_breeds_label
    )
}