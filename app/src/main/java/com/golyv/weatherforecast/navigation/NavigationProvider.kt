package com.golyv.weatherforecast.navigation

import com.golyv.features.home.ui.navigation.HomeApi
import com.golyv.features.launch.ui.navigation.LaunchApi
import com.golyv.features.memories.ui.navigation.MemoriesApi

data class NavigationProvider(val launch:LaunchApi,val home:HomeApi,val memoriesApi: MemoriesApi)
