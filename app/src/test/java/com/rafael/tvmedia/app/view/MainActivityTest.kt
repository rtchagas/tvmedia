package com.rafael.tvmedia.app.view

import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.view.activity.MainActivity
import com.rafael.tvmedia.robolectric.RoboTest
import io.kotest.matchers.shouldBe
import org.junit.Test

class MainActivityTest : RoboTest() {

    @Test
    fun givenActivity_thenCheckInitialFragment() {

        // Prepare
        val scenario = launchActivity<MainActivity>()

        // Assert
        scenario.onActivity {

            val destination: NavDestination? =
                it.findNavController(R.id.nav_host_fragment).currentDestination

            destination?.id shouldBe R.id.fragment_now_playing
        }

        scenario.close()
    }
}
