package com.application.letitsnow.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("name")
    val name: String? = null
)