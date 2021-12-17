package com.rafael.tvmedia.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rafael.tvmedia.domain.usecase.GetMediaEventsUseCase
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.observer.test
import com.rafael.tvmedia.rules.MainCoroutineScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NowPlayingViewModelTest {

    @get:Rule
    val scope = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var mockGetMediaEvents: GetMediaEventsUseCase

    // The test subject
    private lateinit var targetViewModel: NowPlayingViewModel

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the test subject
        targetViewModel = NowPlayingViewModel(
            getMediaEvents = mockGetMediaEvents
        )
    }

    @Test
    fun givenIsRefreshing_whenHasEvents_thenUseCaseIsCalled() = runTest {

        // Prepare
        val mockEvent: MediaEvent = mockk()
        coEvery { mockGetMediaEvents() } returns listOf(mockEvent)

        // Act
        val testObserver = targetViewModel.observeMediaEvents().test()
        targetViewModel.refreshMediaEvents()

        // Assert
        val expected = resultOf(listOf(mockEvent))
        testObserver.assertValue(expected)
        coVerify { mockGetMediaEvents() }
    }
}
