package com.golyv.core.datasource.remote.dataproviders

import android.util.Log
import com.golyv.core.datasource.remote.ApiService
import com.golyv.core.datasource.remote.model.responses.WeatherResponse
import javax.inject.Inject

class WeatherDataProviders @Inject constructor(private val apiService:ApiService) {
    suspend fun getCurrentWeatherInformation(lat:String,lon:String,unit:String)=apiService.getCurrentWeather(lat = lat, lon =lon,unit=unit )
}