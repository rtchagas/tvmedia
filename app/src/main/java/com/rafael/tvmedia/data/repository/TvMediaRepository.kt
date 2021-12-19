package com.rafael.tvmedia.data.repository

import android.net.Uri
import com.rafael.tvmedia.model.MediaEvent

interface TvMediaRepository {

    /**
     * Returns a list with all current media events.
     */
    suspend fun getMediaEvents(): List<MediaEvent>

    /**
     * Adds a media to the liked collection.
     *
     * @param id The media unique ID.
     */
    suspend fun likeMedia(id: Long): Boolean

    /**
     * Adds a media to the not liked collection.
     *
     * @param id The media unique ID.
     */
    suspend fun dislikeMedia(id: Long): Boolean

    /**
     * Adds a media to the watch list collection.
     *
     * @param id The media unique ID.
     */
    suspend fun addMediaToWatchList(id: Long): Boolean

    /**
     * Returns the [Uri] to be used to download the media.
     *
     * @param id The media unique ID.
     */
    suspend fun getMediaDownloadUrl(id: Long): Uri

}
