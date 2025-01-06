package com.golyv.features.launch.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.golyv.core.navigation.HomeFeatureRoutes
import com.golyv.core.navigation.LaunchFeatureRoutes
import com.golyv.core.navigation.MemoriesFeatureRoutes
import com.golyv.core.navigation.NavigationApi
import com.golyv.features.home.ui.screens.home.HomeScreen
import com.golyv.features.launch.ui.screens.LaunchScreen

object InternalLaunchFeatureApi: NavigationApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.navigation(startDestination = LaunchFeatureRoutes.launchScreenRoute, route = LaunchFeatureRoutes.nestedRoute) {
            composable(LaunchFeatureRoutes.launchScreenRoute) {
                LaunchScreen(navController)
            }

        }

    }
}