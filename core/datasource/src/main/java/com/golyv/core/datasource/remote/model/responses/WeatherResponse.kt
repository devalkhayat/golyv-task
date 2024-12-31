package com.golyv.core.datasource.remote.model.responses

import com.golyv.core.datasource.remote.model.dto.CityDTO
import com.golyv.core.datasource.remote.model.dto.WeatherReportDTO
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



data class WeatherResponse(
    @SerializedName("city")
    val city: CityDTO,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherReportDTO>,
    @SerializedName("message")
    val message: Int
)