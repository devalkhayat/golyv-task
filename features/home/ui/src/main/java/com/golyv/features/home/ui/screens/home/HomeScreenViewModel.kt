package com.golyv.features.home.ui.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golyv.core.common.DefaultLocationClient
import com.golyv.core.common.UiEvent
import com.golyv.core.common.components.TemperatureTypes
import com.golyv.features.home.domain.use_cases.GetCurrentWeatherInfoUseCase
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val getCurrentWeatherInfoUseCase: GetCurrentWeatherInfoUseCase):ViewModel() {

    private val _weatherInfo= mutableStateOf(HomeScreenStateHolder())
    val weatherInfo:State<HomeScreenStateHolder> get()=_weatherInfo

     //var weather= mutableStateOf<TemperatureTypes>(TemperatureTypes.F)
     var queryParameters= mutableStateOf(QueryParameters())


    fun getWeatherInfo() {
        viewModelScope.launch {
            queryParameters.value.apply {

                this.latitude?.let {
                    getCurrentWeatherInfoUseCase(this.latitude!!, this.longitude!!,this.temperatureUnit!!.value).onEach {
                        when(it){
                            is UiEvent.Loading -> _weatherInfo.value= HomeScreenStateHolder(isLoading = true)
                            is UiEvent.Success -> _weatherInfo.value= HomeScreenStateHolder(data=it.data)
                            is UiEvent.Error -> _weatherInfo.value= HomeScreenStateHolder(error = it.message.toString())
                        }
                    }.launchIn(viewModelScope)
                }

            }

        }
    }

    fun start(locationClient: DefaultLocationClient) {
        viewModelScope.launch {
            locationClient
                .getLocationUpdates(5000L)
                .catch { e -> Log.d("location_error", "HomeScreen: $e") }
                .onEach { location ->
                    updateLocation(location.latitude.toString(), location.longitude.toString())
                }.launchIn(viewModelScope)
        }
    }

     fun updateLocation(lat:String, lon:String){
            queryParameters.value = queryParameters.value.copy(latitude = lat, longitude = lon)
    }
    fun updateUnit(temperatureUnit: TemperatureTypes){
        queryParameters.value= queryParameters.value.copy(temperatureUnit = temperatureUnit)
    }

}

data class QueryParameters(
    val temperatureUnit:TemperatureTypes?=null,
    val latitude:String?=null,
    val longitude: String?=null)



