package com.golyv.features.home.ui.screens.home

import com.golyv.features.home.domain.model.WeatherInfoModel

data class HomeScreenStateHolder(val isLoading:Boolean=false, val data: WeatherInfoModel?=null, val error:String="")