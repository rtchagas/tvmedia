package com.rafael.tvmedia.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.tvmedia.domain.usecase.GetMediaEventsUseCase
import com.rafael.tvmedia.model.MediaEvent
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val getMediaEvents: GetMediaEventsUseCase
) : ViewModel() {

    private val mediaEventsLiveData = MutableLiveData<Result<List<MediaEvent>>>()

    init {
        refreshMediaEvents()
    }

    fun observeMediaEvents(): LiveData<Result<List<MediaEvent>>> = mediaEventsLiveData

    fun refreshMediaEvents() = viewModelScope.launch {
        mediaEventsLiveData.value = resultOf { sortEvents(getMediaEvents()) }
    }

    private fun sortEvents(events: List<MediaEvent>): List<MediaEvent> =
        events.sortedBy { it.broadcastsOn }

}
