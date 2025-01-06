package com.golyv.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.golyv.core.navigation.HomeFeatureRoutes
import com.golyv.core.navigation.LaunchFeatureRoutes

@Composable
fun AppNavGraph(navController: NavHostController,navigationProvider: NavigationProvider){

    NavHost(navController = navController, startDestination = LaunchFeatureRoutes.nestedRoute ){
        navigationProvider.launch.registerGraph(navController,this)
        navigationProvider.home.registerGraph(navController,this)
        navigationProvider.memoriesApi.registerGraph(navController,this)
    }

}