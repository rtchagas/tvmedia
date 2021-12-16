package com.rafael.tvmedia.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rafael.tvmedia.data.api.TvGuideApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
internal val apiModule = module {

    single<Retrofit> {

        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl("https://tv4-search.b17g.net/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    single<TvGuideApi> { get<Retrofit>().create() }
}
