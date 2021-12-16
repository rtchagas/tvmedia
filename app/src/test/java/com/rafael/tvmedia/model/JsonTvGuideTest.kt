package com.rafael.tvmedia.model

import com.rafael.tvmedia.R
import com.rafael.tvmedia.robolectric.RoboTest
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class JsonTvGuideTest : RoboTest() {

    private val json = Json { ignoreUnknownKeys = true }

    // The test subject
    private lateinit var tvGuide: TvGuide

    @Before
    fun setup() {

        val jsonStr: String = targetContext.resources.openRawResource(R.raw.test_guide)
            .bufferedReader()
            .use { it.readText() }

        tvGuide = json.decodeFromString(jsonStr)
    }

    @Test
    fun givenTvGuide_thenCheckSizeIsCorrect() {

        // Assert
        tvGuide.events shouldHaveSize 3

    }

    @Test
    fun givenTvGuide_whenIsEpisode_thenCheckFields() {

        // Prepare

        // Get the second item
        val episode: MediaEvent = tvGuide.events.component2()

        // Assert

        // ID
        episode.id shouldBe 13735484

        // Broadcast time
        episode.broadcastsOn shouldBe 1639731600000

        // Description
        episode.description shouldBe "Svenskt aktualitetsprogram med Malou von Sivers."

        // Episode number
        episode.episode shouldBe 50

        // Expire time
        episode.expiresOn shouldBe null

        // Image
        episode.image shouldBe "https://img-cdn.b17g.net/932a4ed5-f55f-432c-959c-364ae02279b5/100.jpg"

        // Geo Restriction
        episode.isGeoRestricted shouldBe true

        // Is Live
        episode.isLive shouldBe true

        // Published time
        episode.publishedOn shouldBe 1639652700000

        // Region Availability
        episode.regionAvailability shouldBe "SE"

        // Season
        episode.season shouldBe 32

        // Title
        episode.title shouldBe "Malou efter tio"

        // Type
        episode.type shouldBe MediaEventType.EPISODE
    }

    @Test
    fun givenTvGuide_whenIsClip_thenCheckFields() {

        // Prepare

        // Get the second item
        val clip: MediaEvent = tvGuide.events.component3()

        // Assert

        // ID
        clip.id shouldBe 13735813

        // Broadcast time
        clip.broadcastsOn shouldBe 1639669007000

        // Description
        clip.description shouldBe null

        // Episode number
        clip.episode shouldBe -1

        // Expire time
        clip.expiresOn shouldBe 1639951140000

        // Image
        clip.image shouldBe "https://asset-images.b17g.net/api/v2/img/61bb5d0fe4b09c89287a64e0-1639669007941-.jpg"

        // Geo Restriction
        clip.isGeoRestricted shouldBe false

        // Is Live
        clip.isLive shouldBe false

        // Published time
        clip.publishedOn shouldBe 1639669007000

        // Region Availability
        clip.regionAvailability shouldBe "ALL"

        // Season
        clip.season shouldBe -1

        // Title
        clip.title shouldBe "Vädret Jönköpings, Kronobergs, Kalmar och Blekinge län"

        // Type
        clip.type shouldBe MediaEventType.CLIP
    }
}
