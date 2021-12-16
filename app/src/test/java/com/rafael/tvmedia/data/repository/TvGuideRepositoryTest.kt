package com.rafael.tvmedia.data.repository

import com.rafael.tvmedia.data.api.TvGuideApi
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.model.TvGuide
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TvGuideRepositoryTest {

    @MockK
    private lateinit var mockApi: TvGuideApi

    // The test subject
    private lateinit var targetRepository: TvGuideRepositoryImpl

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the target
        targetRepository = TvGuideRepositoryImpl(api = mockApi)
    }

    @Test
    fun givenApi_whenHasEvents_thenCheckValues() = TestScope().runTest {

        // Prepare
        val mockEvent: MediaEvent = mockk()
        coEvery { mockApi.getTvGuide() } returns TvGuide(listOf(mockEvent))

        // Act
        val events = targetRepository.getMediaEvents()

        // Assert
        events shouldContainExactly listOf(mockEvent)
    }
}
