package com.golyv.weatherforecast

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionsViewModel : ViewModel(){

    val visiblePermissionDialogQueue= mutableStateListOf<String>()
    fun onPermissionResult(
        permission:String,
        isGranted:Boolean
    ){
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission) ){
            visiblePermissionDialogQueue.add(permission)
        }
    }
}