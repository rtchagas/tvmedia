package com.rafael.tvmedia.data.di

/**
 * This must be the only module exposed to outer layers
 */
val dataModules = arrayOf(
    apiModule,
    repositoryModule
)
