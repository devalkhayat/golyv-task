package com.golyv.features.memories.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.golyv.core.navigation.HomeFeatureRoutes
import com.golyv.core.navigation.MemoriesFeatureRoutes
import com.golyv.core.navigation.NavigationApi
import com.golyv.features.memories.ui.screens.capture.CaptureScreen
import com.golyv.features.memories.ui.screens.history.HistoryScreen

internal object InternalMemoriesFeatureApi:NavigationApi {

    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.navigation(startDestination = MemoriesFeatureRoutes.historyScreenRoute, route = MemoriesFeatureRoutes.nestedRoute) {
            composable(MemoriesFeatureRoutes.historyScreenRoute) {
                HistoryScreen()
            }
            composable(MemoriesFeatureRoutes.captureScreenRoute) {
                CaptureScreen(navController)
            }
        }
    }
}