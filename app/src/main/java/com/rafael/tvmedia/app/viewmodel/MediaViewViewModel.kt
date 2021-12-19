package com.rafael.tvmedia.app.viewmodel

import androidx.lifecycle.ViewModel
import com.rafael.tvmedia.model.MediaEvent
import kotlinx.coroutines.delay
import timber.log.Timber

class MediaViewViewModel : ViewModel() {

    /**
     * The media event associated with this view model.
     */
    lateinit var mediaEvent: MediaEvent

    suspend fun like(): Boolean {
        Timber.d("Adding ${mediaEvent.id} to liked media")
        delay(IO_DELAY)
        return true
    }

    suspend fun dislike(): Boolean {
        Timber.d("Adding ${mediaEvent.id} to not liked media")
        delay(IO_DELAY)
        return true
    }

    suspend fun addToWatchList(): Boolean {
        Timber.d("Adding media ${mediaEvent.id} to the watch list")
        delay(IO_DELAY)
        return true
    }

    suspend fun download(): Boolean {
        Timber.d("Starting media ${mediaEvent.id} download...")
        delay(IO_DELAY)
        return true
    }

    companion object {
        private const val IO_DELAY = 500L
    }
}
