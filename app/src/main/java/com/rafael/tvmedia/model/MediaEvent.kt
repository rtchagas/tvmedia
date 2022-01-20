package com.rafael.tvmedia.model


import android.os.Parcelable
import com.rafael.tvmedia.model.serialization.LongAsIso8601DateStringSerializer
import com.rafael.tvmedia.model.serialization.MediaEventTypeSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MediaEvent(

    @SerialName("broadcast_date_time")
    @Serializable(LongAsIso8601DateStringSerializer::class)
    val broadcastsOn: Long,

    @SerialName("description")
    val description: String? = null,

    @SerialName("episode")
    val episode: Int,

    @SerialName("id")
    val id: Long,

    @SerialName("image")
    val image: String? = null,

    @SerialName("is_geo_restricted")
    val isGeoRestricted: Boolean,

    @SerialName("is_live")
    val isLive: Boolean,

    @SerialName("published_date_time")
    @Serializable(LongAsIso8601DateStringSerializer::class)
    val publishedOn: Long,

    @SerialName("region_availibility")
    val regionAvailability: String,

    @SerialName("season")
    val season: Int,

    @SerialName("title")
    val title: String,

    @SerialName("type")
    @Serializable(MediaEventTypeSerializer::class)
    val type: MediaEventType
) : Parcelable {

    fun getTimeToBroadcast(): Long {
        val nowMillis: Long = System.currentTimeMillis()
        return (broadcastsOn - nowMillis)
    }

    fun isPastEvent(): Boolean = (getTimeToBroadcast() < 0)
}

enum class MediaEventType {
    EPISODE,
    CLIP
}
