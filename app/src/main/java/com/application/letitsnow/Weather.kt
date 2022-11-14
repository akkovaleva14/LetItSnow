package com.application.letitsnow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("location")
    val location: Map<String, String>? = emptyMap(),

    @SerialName("current")
    val current: Map<String, String>? = emptyMap(),

    )