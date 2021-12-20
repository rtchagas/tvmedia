package com.rafael.tvmedia.data.repository

import android.net.Uri
import com.rafael.tvmedia.data.api.TvGuideApi
import com.rafael.tvmedia.domain.repository.TvMediaRepository
import com.rafael.tvmedia.model.MediaEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Remote repository implementation using REST to fetch the TV guide content.
 *
 * We could improve this repository by decoupling the api and having
 * data sources instead (local and remote).
 *
 * But for the sake of this demo we'll keep things simple.
 */
internal class TvMediaRepositoryImpl(private val api: TvGuideApi) : TvMediaRepository {

    override suspend fun getMediaEvents(): List<MediaEvent> =
        withContext(Dispatchers.IO) {
            api.getTvGuide().events
        }

    // Dummy
    override suspend fun likeMedia(id: Long): Boolean =
        withContext(Dispatchers.IO) {
            delay(IO_DELAY)
            true
        }

    // Dummy
    override suspend fun dislikeMedia(id: Long): Boolean =
        withContext(Dispatchers.IO) {
            delay(IO_DELAY)
            true
        }

    // Dummy
    override suspend fun addMediaToWatchList(id: Long): Boolean =
        withContext(Dispatchers.IO) {
            delay(IO_DELAY)
            true
        }

    // Dummy
    override suspend fun getMediaDownloadUrl(id: Long): Uri =
        withContext(Dispatchers.IO) {
            delay(IO_DELAY)
            Uri.parse("https://www.google.com")
        }

    companion object {
        private const val IO_DELAY = 200L
    }
}
