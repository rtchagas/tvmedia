package com.rafael.tvmedia.robolectric

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [Build.VERSION_CODES.Q],
    qualifiers = "w720dp-h1280dp-mdpi"
)
abstract class RoboTest : KoinTest {

    protected lateinit var targetContext: Context

    @Before
    fun init() {
        // Get the application context via Robolectric
        targetContext = ApplicationProvider.getApplicationContext()
    }

    @After
    fun dispose() {
        stopKoin()
    }
}
