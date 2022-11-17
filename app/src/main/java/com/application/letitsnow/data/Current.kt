package com.application.letitsnow.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    @SerialName("cloud")
    val cloud: Int,
    @SerialName("condition")
    val condition: Condition,
    @SerialName("feelslike_c")
    val feelslike_c: Double,
    @SerialName("feelslike_f")
    val feelslike_f: Double,
    @SerialName("gust_kph")
    val gust_kph: Double,
    @SerialName("gust_mph")
    val gust_mph: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("is_day")
    val is_day: Int,
    @SerialName("last_updated")
    val last_updated: String,
    @SerialName("last_updated_epoch")
    val last_updated_epoch: Int,
    @SerialName("precip_in")
    val precip_in: Double,
    @SerialName("precip_mm")
    val precip_mm: Double,
    @SerialName("pressure_in")
    val pressure_in: Double,
    @SerialName("pressure_mb")
    val pressure_mb: Double,

    @SerialName("temp_c")
    val temp_c: Double = 0.0,

    @SerialName("temp_f")
    val temp_f: Double,
    @SerialName("uv")
    val uv: Double,
    @SerialName("vis_km")
    val vis_km: Double,
    @SerialName("vis_miles")
    val vis_miles: Double,
    @SerialName("wind_degree")
    val wind_degree: Int,
    @SerialName("wind_dir")
    val wind_dir: String,
    @SerialName("wind_kph")
    val wind_kph: Double,
    @SerialName("wind_mph")
    val wind_mph: Double
)