package com.golyv.features.home.domain.use_cases

import com.golyv.core.common.UiEvent
import com.golyv.features.home.domain.model.WeatherInfoModel
import com.golyv.features.home.domain.repo.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentWeatherInfoUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(latitude: String, longitude: String,unit:String) = flow<UiEvent<WeatherInfoModel>> {
        emit(UiEvent.Loading())
        emit(UiEvent.Success(weatherRepository.getCurrentWeatherInformation(latitude, longitude,unit)))
    }.catch() {
        emit(UiEvent.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}