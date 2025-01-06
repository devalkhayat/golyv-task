package com.golyv.core.common.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
//
val AppBlack = Color(0x00000000)
val AppGray = Color(0xFF1E1E1E)
val AppWhite = Color(0xFFFFFFFF)
val AppLightGray1 = Color(0xFF363636)
val AppLightGray2= Color(0xFF272727)
val AppLightGray3=Color(0xFFB9B9B9)
val AppBlue = Color(0xFF2196F3)
val AppPink = Color(0xFF9C27B0)
val AppRed = Color(0xFFE3005E)
val AppGreen = Color(0xFF4CAF50)

//
fun  getColor(type:AppColors):Color{
    return when(type){
        AppColors.Card -> AppGray
        AppColors.ScreenBackground -> AppBlack
        AppColors.TagBackground -> AppLightGray1
        AppColors.Text1 -> AppWhite
        AppColors.Text2 -> AppLightGray3
        AppColors.CircleProgress-> AppBlue
        AppColors.ContainerBackground1 -> AppLightGray2
        AppColors.ContainerBackground2 -> AppPink
        AppColors.ContainerBackground3 -> AppBlue
        AppColors.Button1 -> AppLightGray1
        AppColors.Button2 -> AppPink
        AppColors.Error -> AppRed
        AppColors.Success -> AppGreen
    }
}
sealed class AppColors{
    data object ScreenBackground: AppColors()
    data object Card: AppColors()
    data object Text1: AppColors()
    data object Text2: AppColors()
    data object TagBackground: AppColors()
    data object CircleProgress:AppColors()
    data object ContainerBackground1:AppColors()
    data object ContainerBackground2:AppColors()
    data object ContainerBackground3:AppColors()
    data object Button1:AppColors()
    data object Button2:AppColors()
    data object Success:AppColors()
    data object Error:AppColors()
}
