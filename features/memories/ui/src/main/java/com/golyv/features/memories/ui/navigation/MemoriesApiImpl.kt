package com.golyv.features.memories.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

class MemoriesApiImpl:MemoriesApi {
    override fun registerGraph(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
        InternalMemoriesFeatureApi.registerGraph(navController,navGraphBuilder)
    }
}