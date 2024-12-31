package com.golyv.features.home.domain.repo

import com.golyv.features.home.domain.model.WeatherInfoModel

interface WeatherRepository {
    suspend fun getCurrentWeatherInformation(latitude:String,longitude:String,unit:String): WeatherInfoModel
}