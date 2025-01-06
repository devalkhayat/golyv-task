package com.golyv.features.memories.ui.screens.history

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.golyv.core.common.R
import com.golyv.core.common.WindowInfo
import com.golyv.core.common.components.AppButton
import com.golyv.core.common.components.AppLabel
import com.golyv.core.common.components.ScreenPlaceHolder
import com.golyv.core.common.components.Section
import com.golyv.core.common.theme.AppColors
import com.golyv.core.common.theme.getColor
import com.golyv.core.common.toKm
import com.golyv.core.common.toPercentage
import com.golyv.core.navigation.MemoriesFeatureRoutes
import com.golyv.features.memories.domain.model.MemoryModel

@Composable
fun HistoryScreen(){

}

@Composable
fun SummarySection(onMove:()->Unit, isShown:Boolean=false){
    if(isShown) {
        val historyScreenViewModel = hiltViewModel<HistoryScreenViewModel>()
        LaunchedEffect(true) {
            historyScreenViewModel.getAll()
        }

        var result = historyScreenViewModel.memoriesList.value

        Column(modifier = Modifier.fillMaxWidth()) {
            Section {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)) {
                    AppLabel(caption = stringResource(R.string.memories), style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(24.dp))

                    if(result.isLoading) CircularProgressIndicator(color = getColor(AppColors.CircleProgress))
                    if(result.error.isNotBlank()) Text(text = result.error)
                    result.data?.let {

                        if(it.size>0) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth().height(130.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                it.forEach {
                                    item { GenerateItem(it) }
                                }

                            }
                        }else{
                            Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center){
                                AppLabel(caption = stringResource(R.string.empty), style = MaterialTheme.typography.labelSmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                            }

                        }
                    }





                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            AppButton(title = stringResource(R.string.take_photo)) {

               onMove()

            }

            Spacer(modifier = Modifier.height(8.dp))
        }


    }
}

@Composable
fun GenerateItem(data: MemoryModel) {
    AsyncImage(
        model = Uri.parse(data.location),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.clip(MaterialTheme.shapes.medium,)

    )
}
