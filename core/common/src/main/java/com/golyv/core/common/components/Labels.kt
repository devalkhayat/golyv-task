package com.golyv.core.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.golyv.core.common.theme.AppColors
import com.golyv.core.common.theme.getColor

@Composable
fun AppLabel(caption:String, style: TextStyle, color: AppColors=AppColors.Text1, maxline:Int=2, textAlign: TextAlign?=null, modifier: Modifier = Modifier){

    Text(text = caption, modifier = modifier, style = style, color = getColor(color), maxLines = maxline, softWrap = true, textAlign = textAlign )
}




