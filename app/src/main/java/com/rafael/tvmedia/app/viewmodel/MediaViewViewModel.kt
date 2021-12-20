package com.rafael.tvmedia.app.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.rafael.tvmedia.domain.usecase.AddMediaEventToWatchListUseCase
import com.rafael.tvmedia.domain.usecase.DislikeMediaEventUseCase
import com.rafael.tvmedia.domain.usecase.DownloadMediaEventUseCase
import com.rafael.tvmedia.domain.usecase.LikeMediaEventUseCase
import com.rafael.tvmedia.model.MediaEvent
import timber.log.Timber

class MediaViewViewModel(
    private val likeMediaEvent: LikeMediaEventUseCase,
    private val dislikeMediaEvent: DislikeMediaEventUseCase,
    private val addMediaEventToWatchList: AddMediaEventToWatchListUseCase,
    private val downloadMediaEvent: DownloadMediaEventUseCase
) : ViewModel() {

    /**
     * The media event associated with this view model.
     */
    lateinit var mediaEvent: MediaEvent

    suspend fun like(): Boolean {
        Timber.d("Adding ${mediaEvent.id} to liked media")
        return likeMediaEvent(mediaEvent.id)
    }

    suspend fun dislike(): Boolean {
        Timber.d("Adding ${mediaEvent.id} to not liked media")
        return dislikeMediaEvent(mediaEvent.id)
    }

    suspend fun addToWatchList(): Boolean {
        Timber.d("Adding media ${mediaEvent.id} to the watch list")
        return addMediaEventToWatchList(mediaEvent.id)
    }

    suspend fun download(): Uri {
        Timber.d("Starting media ${mediaEvent.id} download...")
        return downloadMediaEvent(mediaEvent.id)
    }
}
