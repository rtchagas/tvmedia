package com.rafael.tvmedia.app.di

import com.rafael.tvmedia.app.viewmodel.MediaViewViewModel
import com.rafael.tvmedia.app.viewmodel.NowPlayingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    viewModel {
        NowPlayingViewModel(getMediaEvents = get())
    }

    viewModel {
        MediaViewViewModel(
            likeMediaEvent = get(),
            dislikeMediaEvent = get(),
            addMediaEventToWatchList = get(),
            downloadMediaEvent = get()
        )
    }
}
