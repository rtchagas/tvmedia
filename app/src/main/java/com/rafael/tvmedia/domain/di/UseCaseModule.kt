package com.rafael.tvmedia.domain.di

import com.rafael.tvmedia.domain.usecase.*
import org.koin.dsl.module

internal val useCaseModule = module {

    single {
        GetMediaEventsUseCase(repository = get())
    }

    single {
        LikeMediaEventUseCase(repository = get())
    }

    single {
        DislikeMediaEventUseCase(repository = get())
    }

    single {
        AddMediaEventToWatchListUseCase(repository = get())
    }

    single {
        DownloadMediaEventUseCase(repository = get())
    }
}
