package com.golyv.features.launch.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

class LaunchApiImpl:LaunchApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        InternalLaunchFeatureApi.registerGraph(navController,navGraphBuilder)
    }
}