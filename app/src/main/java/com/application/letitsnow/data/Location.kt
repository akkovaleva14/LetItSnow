package com.application.letitsnow.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    /*@SerialName("country")
    val country: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("localtime")
    val localtime: String,
    @SerialName("localtime_epoch")
    val localtime_epoch: Int,
    @SerialName("lon")
    val lon: Double,*/

    @SerialName("name")
    val name: String? = null
/*
    @SerialName("region")
    val region: String,
    @SerialName("tz_id")
    val tz_id: String*/
)