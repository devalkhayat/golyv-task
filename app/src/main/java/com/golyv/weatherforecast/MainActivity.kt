package com.golyv.weatherforecast

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.golyv.core.common.theme.WeatherforecastTheme
import com.golyv.weatherforecast.navigation.AppNavGraph
import com.golyv.weatherforecast.navigation.NavigationProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissions= arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,

        )

    @Inject
    lateinit var navigationProvider: NavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherforecastTheme {

                var viewModel= viewModel<PermissionsViewModel>()
                var multiplePermissionResultLauncher= rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissions.forEach {
                            viewModel.onPermissionResult(permission=it,isGranted=perms[it]==true)
                        }
                    }
                )

                val navController= rememberNavController()
                App(navHostController = navController,navigationProvider)

                SideEffect {
                    multiplePermissionResultLauncher.launch(permissions)
                }
            }
        }
    }
}



@Composable
fun App(navHostController: NavHostController, navigationProvider: NavigationProvider){

    Scaffold(modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            AppNavGraph(
                navController = navHostController,
                navigationProvider = navigationProvider
            )
        }

    }



}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherforecastTheme {

    }
}