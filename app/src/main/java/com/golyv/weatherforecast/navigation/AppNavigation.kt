package com.golyv.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.golyv.core.navigation.HomeFeatureRoutes

@Composable
fun AppNavGraph(navController: NavHostController,navigationProvider: NavigationProvider){

    NavHost(navController = navController, startDestination = HomeFeatureRoutes.nestedRoute ){

        navigationProvider.home.registerGraph(navController,this)


    }
}