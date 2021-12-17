package com.rafael.tvmedia.domain.di

import com.rafael.tvmedia.domain.usecase.GetMediaEventsUseCase
import org.koin.dsl.module

internal val useCaseModule = module {

    single {
        GetMediaEventsUseCase(
            repository = get()
        )
    }

}
