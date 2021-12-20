package com.rafael.tvmedia.domain.usecase

import com.rafael.tvmedia.domain.repository.TvMediaRepository
import com.rafael.tvmedia.model.MediaEvent
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMediaEventsUseCaseTest {

    @MockK
    private lateinit var mockRepository: TvMediaRepository

    // The test subject
    private lateinit var targetUseCase: GetMediaEventsUseCase

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the test subject
        targetUseCase = GetMediaEventsUseCase(mockRepository)
    }

    @Test
    fun givenUseCase_whenHasEvents_thenCheckValue() = runTest {

        // Prepare
        val mockEvent: MediaEvent = mockk()
        coEvery { mockRepository.getMediaEvents() } returns listOf(mockEvent)

        // Act
        val events = targetUseCase()

        // Assert
        coVerify { mockRepository.getMediaEvents() }
        events shouldContainExactly listOf(mockEvent)
    }
}
