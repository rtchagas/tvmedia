package com.rafael.tvmedia.app.viewmodel

import android.net.Uri
import com.rafael.tvmedia.domain.usecase.AddMediaEventToWatchListUseCase
import com.rafael.tvmedia.domain.usecase.DislikeMediaEventUseCase
import com.rafael.tvmedia.domain.usecase.DownloadMediaEventUseCase
import com.rafael.tvmedia.domain.usecase.LikeMediaEventUseCase
import com.rafael.tvmedia.model.MediaEvent
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MediaViewViewModelTest {

    @MockK
    private lateinit var mockLikeMediaEvent: LikeMediaEventUseCase

    @MockK
    private lateinit var mockDislikeMediaEvent: DislikeMediaEventUseCase

    @MockK
    private lateinit var mockAddMediaEventToWatchList: AddMediaEventToWatchListUseCase

    @MockK
    private lateinit var mockDownloadMediaEvent: DownloadMediaEventUseCase

    // The test subject
    private lateinit var targetViewModel: MediaViewViewModel

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the target view model
        targetViewModel = MediaViewViewModel(
            likeMediaEvent = mockLikeMediaEvent,
            dislikeMediaEvent = mockDislikeMediaEvent,
            addMediaEventToWatchList = mockAddMediaEventToWatchList,
            downloadMediaEvent = mockDownloadMediaEvent
        )

        val mockEvent: MediaEvent = mockk {
            every { id } returns 123
        }

        targetViewModel.mediaEvent = mockEvent
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun givenMediaEvent_whenLike_thenCheckSuccess() = runTest {

        // Prepare
        coEvery { mockLikeMediaEvent(any()) } returns true

        // Act
        val result = targetViewModel.like()

        // Assert
        coVerify { mockLikeMediaEvent(123) }
        result shouldBe true
    }

    @Test
    fun givenMediaEvent_whenDislike_thenCheckSuccess() = runTest {

        // Prepare
        coEvery { mockDislikeMediaEvent(any()) } returns true

        // Act
        val result = targetViewModel.dislike()

        // Assert
        coVerify { mockDislikeMediaEvent(123) }
        result shouldBe true
    }

    @Test
    fun givenMediaEvent_whenAddToWatchList_thenCheckSuccess() = runTest {

        // Prepare
        coEvery { mockAddMediaEventToWatchList(any()) } returns true

        // Act
        val result = targetViewModel.addToWatchList()

        // Assert
        coVerify { mockAddMediaEventToWatchList(123) }
        result shouldBe true
    }

    @Test
    fun givenMediaEvent_whenDownloadMedia_thenCheckUri() = runTest {

        // Prepare
        val mockUri: Uri = mockk()
        coEvery { mockDownloadMediaEvent(any()) } returns mockUri

        // Act
        val result = targetViewModel.download()

        // Assert
        coVerify { mockDownloadMediaEvent(123) }
        result shouldBe mockUri
    }
}