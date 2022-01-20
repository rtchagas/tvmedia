package com.rafael.tvmedia.model.serialization

import com.rafael.tvmedia.model.MediaEventType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit

/**
 * Custom serializer to transform ISO 8601 dates like
 * 2021-12-16T15:42:37Z or 2021-12-16T15:42:37+01:00
 * into milliseconds since epoch.
 */
object LongAsIso8601DateStringSerializer : KSerializer<Long> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LongAsIso8601Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Long) {
        // We always encode in the GMT offset
        val encoded = Instant.ofEpochMilli(value).toString()
        encoder.encodeString(encoded)
    }

    override fun deserialize(decoder: Decoder): Long {
        val iso8601Str = decoder.decodeString()
        // We consider the offset while decoding
        return OffsetDateTime.parse(iso8601Str).toEpochMilli()
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

/**
 * Converts this date-time to the number of milliseconds from
 * the epoch of 1970-01-01T00:00:00Z.
 */
fun OffsetDateTime.toEpochMilli(): Long =
    TimeUnit.SECONDS.toMillis(this.toEpochSecond())
