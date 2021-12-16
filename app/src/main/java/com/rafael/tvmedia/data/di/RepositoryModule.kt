package com.rafael.tvmedia.data.di

import com.rafael.tvmedia.data.repository.TvGuideRepository
import com.rafael.tvmedia.data.repository.TvGuideRepositoryImpl
import org.koin.dsl.module

internal val repositoryModule = module {

    single<TvGuideRepository> {
        TvGuideRepositoryImpl(api = get())
    }

}
