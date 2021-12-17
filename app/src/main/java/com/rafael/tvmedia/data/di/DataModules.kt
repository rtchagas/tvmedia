package com.rafael.tvmedia.data.di

/**
 * In a modular approach, this should be the only module list exposed to outer layers.
 */
val dataModules = arrayOf(
    apiModule,
    repositoryModule
)
