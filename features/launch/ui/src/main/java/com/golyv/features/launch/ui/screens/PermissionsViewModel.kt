package com.golyv.features.launch.ui.screens

import android.Manifest
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel(){


    var visiblePermissions= mutableStateListOf<VisiblePermission>()
    val permissions= arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,

        )

}

data class VisiblePermission(var name:String, var isGrant:Boolean)
