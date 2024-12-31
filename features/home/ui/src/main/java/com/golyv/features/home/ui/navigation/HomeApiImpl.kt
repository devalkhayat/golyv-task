package com.golyv.features.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

class HomeApiImpl:HomeApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        InternalHomeFeatureApi.registerGraph(navController,navGraphBuilder)
    }
}