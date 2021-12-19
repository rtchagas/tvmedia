package com.rafael.tvmedia.data.di

import com.rafael.tvmedia.data.repository.TvMediaRepository
import com.rafael.tvmedia.data.repository.TvMediaRepositoryImpl
import org.koin.dsl.module

internal val repositoryModule = module {

    single<TvMediaRepository> {
        TvMediaRepositoryImpl(api = get())
    }
}
