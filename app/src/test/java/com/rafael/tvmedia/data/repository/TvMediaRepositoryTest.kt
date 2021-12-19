package com.rafael.tvmedia.data.repository

import android.net.Uri
import com.rafael.tvmedia.data.api.TvGuideApi
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.model.TvGuide
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TvMediaRepositoryTest {

    @MockK
    private lateinit var mockApi: TvGuideApi

    // The test subject
    private lateinit var targetRepository: TvMediaRepositoryImpl

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the target
        targetRepository = TvMediaRepositoryImpl(api = mockApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun givenApi_whenHasEvents_thenCheckValues() = runTest {

        // Prepare
        val mockEvent: MediaEvent = mockk()
        coEvery { mockApi.getTvGuide() } returns TvGuide(listOf(mockEvent))

        // Act
        val events = targetRepository.getMediaEvents()

        // Assert
        coVerify { mockApi.getTvGuide() }
        events shouldContainExactly listOf(mockEvent)
    }

    @Test
    fun givenIsDummy_whenLikeMedia_thenSuccess() = runTest {

        // Act
        val result = targetRepository.likeMedia(123)

        // Assert
        result shouldBe true
    }

    @Test
    fun givenIsDummy_whenDislikeMedia_thenSuccess() = runTest {

        // Act
        val result = targetRepository.dislikeMedia(123)

        // Assert
        result shouldBe true
    }

    @Test
    fun givenIsDummy_whenAddToWatchList_thenSuccess() = runTest {

        // Act
        val result = targetRepository.addMediaToWatchList(123)

        // Assert
        result shouldBe true
    }

    @Test
    fun givenIsDummy_whenDownloadMedia_thenCheckUri() = runTest {

        // Prepare
        mockkStatic(Uri::class)
        val mockUri: Uri = mockk()
        every { Uri.parse("https://www.google.com") } returns mockUri

        // Act
        val result = targetRepository.getMediaDownloadUrl(123)

        // Assert
        result shouldBe mockUri
    }
}
