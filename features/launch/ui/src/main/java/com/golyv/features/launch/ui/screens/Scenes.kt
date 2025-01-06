package com.golyv.features.launch.ui.screens

sealed class LaunchScreenStateHolder {
    data object Loading : LaunchScreenStateHolder()
    data object RequirePermission : LaunchScreenStateHolder()
}