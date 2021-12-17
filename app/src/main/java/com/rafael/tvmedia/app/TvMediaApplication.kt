package com.rafael.tvmedia.app

import android.app.Application
import com.rafael.tvmedia.BuildConfig
import com.rafael.tvmedia.app.di.appModules
import com.rafael.tvmedia.data.di.dataModules
import com.rafael.tvmedia.domain.di.domainModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.*


class TvMediaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initKoin() =
        startKoin {
            androidLogger()
            androidContext(this@TvMediaApplication)
            modules(
                *dataModules,
                *domainModules,
                *appModules
            )
        }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
