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
class AddMediaEventToWatchListUseCaseTest {

    @MockK
    private lateinit var mockRepository: TvMediaRepository

    // The test subject
    private lateinit var targetUseCase: AddMediaEventToWatchListUseCase

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the test subject
        targetUseCase = AddMediaEventToWatchListUseCase(mockRepository)
    }

    @Test
    fun givenUseCase_whenAddMediaEventToWatchList_thenCheckResult() = runTest {

        // Prepare
        coEvery { mockRepository.addMediaToWatchList(any()) } returns true

        // Act
        val result: Boolean = targetUseCase(123)

        // Assert
        coVerify { mockRepository.addMediaToWatchList(123) }
        result shouldBe true
    }
}
