package com.golyv.features.home.data.repo

import android.util.Log
import com.golyv.core.common.UiEvent
import com.golyv.core.datasource.remote.dataproviders.WeatherDataProviders
import com.golyv.features.home.data.mappers.toDomainInstance
import com.golyv.features.home.domain.model.WeatherInfoModel
import com.golyv.features.home.domain.repo.WeatherRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(private val weatherDataProviders: WeatherDataProviders) : WeatherRepository{
    override suspend fun getCurrentWeatherInformation(
        latitude: String,
        longitude: String,
        unit:String
    ): WeatherInfoModel =  weatherDataProviders.getCurrentWeatherInformation(lat = latitude, lon = longitude,unit=unit).toDomainInstance()

}