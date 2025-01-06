package com.golyv.features.launch.ui.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.golyv.core.common.R
import com.golyv.core.common.components.AnimatedPreloader
import com.golyv.core.common.components.AppButton
import com.golyv.core.common.components.AppLabel
import com.golyv.core.common.components.Section
import com.golyv.core.common.theme.AppColors
import com.golyv.core.common.theme.getColor
import com.golyv.core.navigation.HomeFeatureRoutes
import com.golyv.core.navigation.LaunchFeatureRoutes
import com.golyv.core.navigation.MemoriesFeatureRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LaunchScreen(navController: NavHostController){
    var currentState by remember  { mutableStateOf<LaunchScreenStateHolder>(LaunchScreenStateHolder.Loading) }

    LaunchedEffect(true) {
        delay(2000L)
        currentState=LaunchScreenStateHolder.RequirePermission
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(R.drawable.background),
            contentScale = ContentScale.FillBounds
        )){
        when(currentState){
            LaunchScreenStateHolder.Loading ->  AnimatedPreloader()
            LaunchScreenStateHolder.RequirePermission -> CheckingPermissions(navController)
        }
    }
}

@Composable
fun CheckingPermissions(navController: NavHostController){

    var viewModel= viewModel<PermissionsViewModel>()
    val context= LocalContext.current
    var isShowDialog by remember { mutableStateOf(false) }
    viewModel.permissions.forEach {

        if (ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED) {
            viewModel.visiblePermissions.add(VisiblePermission(it, true))
        }else{

            viewModel.visiblePermissions.add(VisiblePermission(it, false))
            isShowDialog=true
        }
    }

    if(isShowDialog){

        RequestPermissionScreen(navController)

    }else{

        moveToHome(navController)

    }




}

@Composable
fun RequestPermissionScreen(navController: NavHostController) {

    val activity = (LocalContext.current as? Activity)
    var viewModel= viewModel<PermissionsViewModel>()
    var remcor= rememberCoroutineScope()
    var multiplePermissionResultLauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            var numOfGranted=0

           for ( pr in perms){

               var index=0
               viewModel.visiblePermissions.forEach {
                    if(pr.key==it.name){
                        viewModel.visiblePermissions[index]=viewModel.visiblePermissions[index].copy(name = it.name, isGrant = pr.value)
                    }
                   index++
               }
               if(pr.value) ++numOfGranted
           }

            if(numOfGranted==viewModel.permissions.size){

                moveToHome(navController)
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
            ),
        contentAlignment = Alignment.Center
    ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {


                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    AppLabel(
                        caption = "Request Permission",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                viewModel.visiblePermissions.forEach {

                    PermissionLayout("${it.name} permission is required", it.isGrant)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),) {
                    Column {
                        AppButton(title = "Enable") {
                            remcor.launch {
                                multiplePermissionResultLauncher.launch(viewModel.permissions)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        AppButton(title = "Exit") {
                            activity?.finish()
                        }
                    }


                }



            }



    }
}
@Composable
fun PermissionLayout(name:String,status:Boolean){


    Section(round=MaterialTheme.shapes.medium) {

        Box(modifier = Modifier.padding(8.dp)){
            Row {
                Icon(
                    painter = painterResource( when(status){
                        true->R.drawable.ic_correct
                        false->R.drawable.ic_error
                    }),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                AppLabel(name, style = MaterialTheme.typography.labelSmall,color=when(status){
                    true->AppColors.Success
                    false->AppColors.Error
                }, modifier = Modifier.padding(top = 8.dp))

            }

        }

    }

}

fun moveToHome(navController: NavHostController){

    navController.navigate(HomeFeatureRoutes.homeScreenRoute) {
        popUpTo(LaunchFeatureRoutes.launchScreenRoute) {
             inclusive = true
        }
    }
}

