package com.golyv.features.home.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.golyv.core.common.WindowInfo
import com.golyv.core.common.components.AppLabel
import com.golyv.core.common.rememberWindowInfo
import com.golyv.core.common.theme.AppColors
import com.golyv.core.common.theme.getColor
import com.golyv.features.home.domain.model.WeatherInfoModel
import coil.compose.AsyncImage
import com.golyv.core.common.DefaultLocationClient
import com.golyv.core.common.components.ScreenPlaceHolder
import com.golyv.core.common.components.Section
import com.golyv.core.common.components.TemperatureTypes
import com.golyv.core.common.components.TemperatureTypesDropDown
import com.golyv.core.common.toDegree
import com.golyv.core.common.toKm
import com.golyv.core.common.toPercentage
import com.golyv.core.common.R
import com.golyv.features.memories.ui.screens.history.SummarySection
import com.google.android.gms.location.LocationServices




@Composable
fun HomeScreen(navHostController: NavHostController){
    val context = LocalContext.current.applicationContext
    val win= rememberWindowInfo()
    val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
    //unit
    LaunchedEffect(Unit) {

        homeScreenViewModel.queryParameters.value=homeScreenViewModel.queryParameters.value.copy(temperatureUnit = TemperatureTypes.C)
        //location
        val locationClient=DefaultLocationClient(
            context = context,
            client = LocationServices.getFusedLocationProviderClient(context))
        homeScreenViewModel.start(locationClient)
    }


    LaunchedEffect(homeScreenViewModel.queryParameters.value){
        homeScreenViewModel.getWeatherInfo()
    }

    val result=homeScreenViewModel.weatherInfo.value

    if(result.isLoading){
        ScreenPlaceHolder{
            CircularProgressIndicator(color = getColor(AppColors.CircleProgress))
        }
    }
    if(result.error.isNotBlank()){
        ScreenPlaceHolder{
            Text(text = result.error)
        }
    }
    result.data?.let {

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            when (win.screenWidthInfo) {
                WindowInfo.WindowType.Compact -> CompactLayout(navHostController,it, homeScreenViewModel)
                WindowInfo.WindowType.Expanded -> ExpandedLayout(navHostController,it,homeScreenViewModel)
                WindowInfo.WindowType.Medium -> MediumLayout(navHostController,it,homeScreenViewModel)
            }
        }

    }

}


@Composable
fun CompactLayout(navHostController: NavHostController,data:WeatherInfoModel,viewModel: HomeScreenViewModel) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)) {
        WeatherInfoSection(data,viewModel)
        HighLightSection(data)
        SummarySection(navHostController,true)
    }
}
@Composable
fun MediumLayout(navHostController: NavHostController,data:WeatherInfoModel,viewModel: HomeScreenViewModel){

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
        .padding(4.dp),

    ) {
        WeatherInfoSection(data,viewModel)
        HighLightSection(data)
        SummarySection(navHostController,true)
    }
}
@Composable
fun ExpandedLayout(navHostController: NavHostController,data:WeatherInfoModel,viewModel: HomeScreenViewModel) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()
        .padding(4.dp)) {
        WeatherInfoSection(data,viewModel)
        HighLightSection(data)
        SummarySection(navHostController,true)
    }
}


/////////////Top Section
@Composable
fun WeatherInfoSection(data:WeatherInfoModel,viewModel: HomeScreenViewModel){
    return Section{
        data.let {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp)) {

                CurrentLocation(it.city,viewModel)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {

                    Day(dayName = it.day,date=it.dateTime, url = it.status.icon, modifier = Modifier.weight(1f))
                    Degrees(type = viewModel.queryParameters.value.temperatureUnit!!.short,min=it.temperature.minDegree,max=it.temperature.maxDegree,status=it.status.name,feelsLikeDegree=it.status.feelLikeDegree,modifier = Modifier.weight(1f))
                }


            }
        }
    }

}
@Composable
fun CurrentLocation(city:String,viewModel: HomeScreenViewModel) {

    return Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(getColor(AppColors.TagBackground))
        ) {
            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Image(painter = painterResource(com.golyv.core.common.R.drawable.ic_current_location), contentDescription = "location")
                Spacer(modifier = Modifier.width(8.dp))
                AppLabel(caption = city, style = MaterialTheme.typography.labelLarge)
            }
        }

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(getColor(AppColors.TagBackground))
        ) {
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)) {
                TemperatureTypesDropDown {
                    viewModel.updateUnit(it)
                }
            }
        }


    }
}

@Composable
fun Day(dayName:String,date:String,url:String,modifier: Modifier=Modifier){
    return  Column(modifier = modifier.fillMaxWidth()) {

        AppLabel(caption = dayName, style = MaterialTheme.typography.titleMedium)
        AppLabel(caption = date, style = MaterialTheme.typography.labelSmall)
        WeatherIcon(url = url)
    }
}
@Composable
fun WeatherIcon(url:String){
    return  AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .size(80.dp)

    )
}
@Composable
fun Degrees(type:String,min:String,max:String,status:String,feelsLikeDegree:String,modifier: Modifier=Modifier){
    return Column(modifier = modifier.fillMaxWidth()) {

        AppLabel(caption = max.toDegree(type), style = MaterialTheme.typography.titleLarge)
        AppLabel(caption = "/${min.toDegree(type)}", color = AppColors.Text2, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(16.dp))
        AppLabel(caption = status, style = MaterialTheme.typography.labelMedium)
        AppLabel(caption = "${stringResource(R.string.feels_like)} ${feelsLikeDegree.toDegree(type)}", style = MaterialTheme.typography.labelSmall)

    }
}

/////////////Bottom Section
@Composable
fun HighLightSection(data: WeatherInfoModel) {
    val cellCount = 2
    return Section {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)) {
            AppLabel(caption = stringResource(R.string.today_highlight), style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                columns = GridCells.Fixed(cellCount),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 30.dp, top = 10.dp, start = 5.dp),
            ) {
                item { // Two items }
                    HighLightInfo(title = stringResource(R.string.wind_status), value = data.temperature.windSpeed.toKm(), background = AppColors.ContainerBackground2, icon = com.golyv.core.common.R.drawable.ic_wind)
                }
                item { // Two items }
                    HighLightInfo(title = stringResource(R.string.humidity), value = data.temperature.humidity.toPercentage(), background = AppColors.ContainerBackground3, icon = com.golyv.core.common.R.drawable.ic_humidity)
                }
            }

        }







    }
}
@Composable
fun HighLightInfo(title:String,background:AppColors,value:String,@DrawableRes icon :Int){
    Section(background = background, round = MaterialTheme.shapes.medium) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),) {

            Row{
                Image(painter = painterResource(icon),contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                AppLabel(caption = title, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            AppLabel(caption = value, style = MaterialTheme.typography.titleSmall)

        }
    }

}





