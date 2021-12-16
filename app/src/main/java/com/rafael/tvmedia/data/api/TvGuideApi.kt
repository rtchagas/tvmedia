package com.rafael.tvmedia.data.api

import com.rafael.tvmedia.model.TvGuide
import retrofit2.http.GET

internal interface TvGuideApi {

    @GET(API_PATH_ASSETS)
    suspend fun getTvGuide(): TvGuide

    companion object {
        private const val API_PATH_ASSETS = "assets?client=android-code-test"
    }
}
