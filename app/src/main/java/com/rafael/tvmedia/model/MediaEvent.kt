package com.rafael.tvmedia.model


import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

@Serializable
data class MediaEvent(

    @SerialName("broadcast_date_time")
    @Serializable(LongAsDateStringSerializer::class)
    val broadcastsOn: Long,

    @SerialName("description")
    val description: String? = null,

    @SerialName("episode")
    val episode: Int,

    @SerialName("expire_date_time")
    @Serializable(LongAsDateStringSerializer::class)
    val expiresOn: Long? = null,

    @SerialName("id")
    val id: Long,

    @SerialName("image")
    val image: String,

    @SerialName("is_geo_restricted")
    val isGeoRestricted: Boolean,

    @SerialName("is_live")
    val isLive: Boolean,

    @SerialName("published_date_time")
    @Serializable(LongAsDateStringSerializer::class)
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
)

enum class MediaEventType {
    EPISODE,
    CLIP
}

/**
 * Custom serializer to transform ISO dates (2021-12-16T15:42:37Z)
 * into milliseconds since epoch.
 */
object LongAsDateStringSerializer : KSerializer<Long> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LongDate", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Long) {
        val encoded = Instant.ofEpochSecond(value).toString()
        encoder.encodeString(encoded)
    }

    override fun deserialize(decoder: Decoder): Long {
        return Instant.parse(decoder.decodeString()).toEpochMilli()
    }
}

/**
 * Custom serializer to transform lower case strings into MediaEventType Enum.
 */
object MediaEventTypeSerializer : KSerializer<MediaEventType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MediaEventType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: MediaEventType) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): MediaEventType {
        return MediaEventType.valueOf(decoder.decodeString().uppercase())
    }
}