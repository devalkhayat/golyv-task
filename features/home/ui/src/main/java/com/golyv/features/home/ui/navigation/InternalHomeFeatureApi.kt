package com.golyv.features.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.golyv.core.navigation.HomeFeatureRoutes
import com.golyv.core.navigation.MemoriesFeatureRoutes
import com.golyv.core.navigation.NavigationApi
import com.golyv.features.home.ui.screens.home.HomeScreen
import com.golyv.features.memories.ui.screens.capture.CaptureScreen

internal object InternalHomeFeatureApi: NavigationApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.navigation(startDestination = HomeFeatureRoutes.homeScreenRoute, route = HomeFeatureRoutes.nestedRoute) {
            composable(HomeFeatureRoutes.homeScreenRoute) {

                HomeScreen(navController)
            }
            composable(MemoriesFeatureRoutes.nestedRoute) {

                CaptureScreen(navController)
            }
        }

    }

}