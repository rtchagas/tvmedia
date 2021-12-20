package com.rafael.tvmedia.domain.usecase

import com.rafael.tvmedia.domain.repository.TvMediaRepository
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DislikeMediaEventUseCaseTest {

    @MockK
    private lateinit var mockRepository: TvMediaRepository

    // The test subject
    private lateinit var targetUseCase: DislikeMediaEventUseCase

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the test subject
        targetUseCase = DislikeMediaEventUseCase(mockRepository)
    }

    @Test
    fun givenUseCase_whenDislikeMediaEvent_thenCheckCheckResult() = runTest {

        // Prepare
        coEvery { mockRepository.dislikeMedia(any()) } returns true

        // Act
        val result: Boolean = targetUseCase(123)

        // Assert
        coVerify { mockRepository.dislikeMedia(123) }
        result shouldBe true
    }
}
