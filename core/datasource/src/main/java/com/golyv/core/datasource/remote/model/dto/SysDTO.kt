package com.golyv.core.datasource.remote.model.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class SysDTO(
    @SerializedName("pod")
    val pod: String
)