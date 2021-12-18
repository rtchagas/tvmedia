package com.rafael.tvmedia.app.util

import android.content.Context
import com.rafael.tvmedia.R
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.model.TvGuide
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object FakeDataUtil {

    private val json = Json { ignoreUnknownKeys = true }

    fun getFakeMediaItems(context: Context): List<MediaEvent> {

        val jsonStr: String = context.resources.openRawResource(R.raw.test_guide)
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString<TvGuide>(jsonStr).events
    }
}
