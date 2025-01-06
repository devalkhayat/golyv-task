package com.golyv.features.memories.ui.screens.capture


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.MediaPlayer
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.golyv.core.common.MediaStoreUtil
import com.golyv.core.common.R
import com.golyv.core.common.components.AppButton
import com.golyv.core.common.theme.AppColors
import com.golyv.core.navigation.HomeFeatureRoutes
import com.golyv.core.navigation.MemoriesFeatureRoutes

import com.golyv.features.memories.ui.screens.history.HistoryScreenViewModel
import kotlinx.coroutines.launch


@Composable
fun CaptureScreen(navHostController: NavHostController){

    var isPreview by rememberSaveable { mutableStateOf(false) }
    var capturedBitmap by rememberSaveable{  mutableStateOf<Bitmap?>(null)}

    if(!isPreview){
        CaptureView{status,bitmap->
            isPreview=status
            capturedBitmap=bitmap
        }
    }else {
        capturedBitmap?.let {
            PreviewCapturedView(navHostController,it) { status ->
                isPreview = status
            }
        }
    }



}



@Composable
fun CaptureView(action:(isPreview:Boolean,bitmap:Bitmap)->Unit){
    val context=LocalContext.current
    val controller= remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    controller.cameraSelector= CameraSelector.DEFAULT_BACK_CAMERA
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

        Box(modifier = Modifier.height(300.dp)) {
            CameraPreview(controller, modifier = Modifier.fillMaxSize())
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(60.dp)){
            AppButton(title = stringResource(R.string.capture)) {
                takePhoto(context,controller){bitmap: Bitmap ->
                        action(true,bitmap)

                    val mPlayer: MediaPlayer = MediaPlayer.create(context,R.raw.camera_shutter)
                    mPlayer.start()
                }
            }
        }


    }

}

@Composable
fun PreviewCapturedView(navHostController: NavHostController,bitmap: Bitmap,action:(isPreview:Boolean)->Unit){
    val context=LocalContext.current
    val savingCoroutines= rememberCoroutineScope()
    val historyScreenViewModel = hiltViewModel<HistoryScreenViewModel>()

    Column(modifier = Modifier.fillMaxSize()) {

        AsyncImage(
            model = bitmap,
            contentDescription = null,
            modifier = Modifier.clip(MaterialTheme.shapes.medium)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(60.dp)){
            Column (modifier = Modifier.fillMaxWidth()) {

                AppButton(title = stringResource(R.string.save)) {
                    savingCoroutines.launch {
                        //save to local
                       var uri= MediaStoreUtil(context).saveImage(bitmap)
                        //save to room
                        historyScreenViewModel.add(uri)
                        // go to home
                        navHostController.navigate(HomeFeatureRoutes.homeScreenRoute) {
                            popUpTo(MemoriesFeatureRoutes.captureScreenRoute) {
                                inclusive = true
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(title = stringResource(R.string.recapture), background = AppColors.Button2) {
                     action(false)
                }

            }
        }
    }
}

private fun takePhoto(context: Context, controller: LifecycleCameraController, onPhotoTaken:(Bitmap)->Unit) {

    controller.takePicture(ContextCompat.getMainExecutor(context), object :
        OnImageCapturedCallback() {

        override fun onCaptureSuccess(image: ImageProxy) {
            super.onCaptureSuccess(image)
            //for rotation
            val matrix= Matrix().apply {
                postRotate(image.imageInfo.rotationDegrees.toFloat())
            }
            val rotatedBitmap=Bitmap.createBitmap(
                image.toBitmap(),0,0,image.width,image.height,matrix,true
            )
            onPhotoTaken(rotatedBitmap)
        }

        override fun onError(exception: ImageCaptureException) {
            super.onError(exception)
            Log.d("Camera_Capture", "onError: ${exception.message}")
        }
    }

    )

}