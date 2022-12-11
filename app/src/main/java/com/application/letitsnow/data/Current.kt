package com.application.letitsnow.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("temp_c")
    val temp_c: Double = 0.0
)