package com.rafael.tvmedia.data.repository

import com.rafael.tvmedia.data.api.TvGuideApi
import com.rafael.tvmedia.model.MediaEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Remote repository implementation using REST to fetch the TV guide content.
 *
 * We could improve this repository by decoupling the api and having
 * data sources instead (local and remote).
 *
 * But for the sake of this demo we'll keep things simple.
 */
internal class TvGuideRepositoryImpl(private val api: TvGuideApi) : TvGuideRepository {

    override suspend fun getMediaEvents(): List<MediaEvent> =
        withContext(Dispatchers.IO) {
            api.getTvGuide().events
        }

}
