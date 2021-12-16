package com.rafael.tvmedia.data.repository

import com.rafael.tvmedia.model.MediaEvent

interface TvGuideRepository {

    /**
     * Returns a list with all current media events.
     */
    suspend fun getMediaEvents(): List<MediaEvent>

}
