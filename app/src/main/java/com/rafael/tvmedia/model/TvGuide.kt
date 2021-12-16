package com.rafael.tvmedia.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvGuide(
    @SerialName("data")
    val events: List<MediaEvent>
)
