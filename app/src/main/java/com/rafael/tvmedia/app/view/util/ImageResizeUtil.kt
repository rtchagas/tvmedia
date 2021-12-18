package com.rafael.tvmedia.app.view.util

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * Generates proxyed image URLs for resizing purposes.
 */
object ImageResizeUtil {

    // Lets get some help from OkHttp to proper format and encode the final URL
    private val imageProxyUrl = "https://imageproxy.b17g.services/convert".toHttpUrl()

    fun resize(originalUrl: String, width: Int = 0, height: Int = 0): HttpUrl {

        val builder = imageProxyUrl.newBuilder()

        // Add the encoded source
        builder.addQueryParameter("source", originalUrl)

        val widthStr = if (width > 0) "$width" else ""
        val heightStr = if (height > 0) "$height" else ""

        // Add the resize parameters
        builder.addQueryParameter("resize", "${widthStr}x${heightStr}")

        return builder.build()
    }
}
