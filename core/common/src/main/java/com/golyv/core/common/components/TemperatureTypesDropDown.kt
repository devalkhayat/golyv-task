package com.golyv.core.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.golyv.core.common.R
import com.golyv.core.common.theme.AppColors
import com.golyv.core.common.theme.getColor
import com.golyv.core.common.toDegree

@Composable
fun TemperatureTypesDropDown(action:(selected:TemperatureTypes)->Unit){

    val list= listOf(TemperatureTypes.C,TemperatureTypes.F)
    val isExpandend = remember {
        mutableStateOf(false)
    }
    val currentValue = remember {
        mutableStateOf(list[0])
    }

    Row(modifier = Modifier.clickable { isExpandend.value = !isExpandend.value }, verticalAlignment = Alignment.CenterVertically) {
        AppLabel(caption = currentValue.value.short+ "\u00B0", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(painter = painterResource(id = R.drawable.ic_arrow_down), contentDescription = "drop", tint = getColor(AppColors.Text1) )
        Spacer(modifier = Modifier.width(8.dp))

        DropdownMenu(
            expanded = isExpandend.value,
            onDismissRequest = { isExpandend.value = false }) {

            list.forEach { DropdownMenuItem(
                text = { AppLabel(caption = it.name, style = MaterialTheme.typography.labelSmall)  },
                onClick = {
                    currentValue.value = it
                    isExpandend.value = false
                    action(it)
                }
            ) }


        }
    }
}

sealed class TemperatureTypes(val short:String, val name:String, var value:String){
    data object C:TemperatureTypes(short="C",name="Celsius",value="metric")
    data object F:TemperatureTypes(short="F",name="Fahrenheit",value="imperial")
}