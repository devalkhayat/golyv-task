package com.golyv.core.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.golyv.core.common.theme.AppColors
import com.golyv.core.common.theme.getColor

@Composable
fun Section(background: AppColors = AppColors.Card, round: Shape = MaterialTheme.shapes.large, modifier: Modifier = Modifier, content: @Composable ()-> Unit){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(8.dp)
            .clip(round)
            .background(getColor(background)),
    ){
        content()
    }
}
@Composable
fun ScreenPlaceHolder(content: @Composable ()->Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        content()
    }
}