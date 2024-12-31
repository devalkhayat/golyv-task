package com.golyv.features.home.data.mappers

import android.util.Log
import com.golyv.core.common.Constants
import com.golyv.core.common.toDate
import com.golyv.core.common.toDayName
import com.golyv.core.datasource.remote.model.responses.WeatherResponse
import com.golyv.features.home.domain.model.TemperatureModel
import com.golyv.features.home.domain.model.WeatherInfoModel
import com.golyv.features.home.domain.model.WeatherStatusModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun WeatherResponse.toDomainInstance():WeatherInfoModel{

    val current= this.list[0]
    val temp=TemperatureModel(maxDegree = current.main.tempMax.toInt().toString(),
        minDegree = current.main.tempMin.toInt().toString(),
        humidity = current.main.humidity.toInt().toString(),
        windSpeed = current.wind.speed.toInt().toString())

    val status=WeatherStatusModel(name= current.weather[0].main,
        icon ="${Constants.BASE_IMAGE_URL}${current.weather[0].icon}.png",
        feelLikeDegree = current.main.feelsLike.toInt().toString())

    val weatherInfoModel=WeatherInfoModel(city=this.city.name,
        day = current.dt.toDayName(),
        dateTime =current.dt.toDate(),
        temperature = temp,
        status =status )

    return  weatherInfoModel
}

