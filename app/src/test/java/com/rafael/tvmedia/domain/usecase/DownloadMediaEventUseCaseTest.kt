package com.rafael.tvmedia.domain.usecase

import android.net.Uri
import com.rafael.tvmedia.data.repository.TvMediaRepository
import io.kotest.matchers.shouldBe
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
class DownloadMediaEventUseCaseTest {

    @MockK
    private lateinit var mockRepository: TvMediaRepository

    // The test subject
    private lateinit var targetUseCase: DownloadMediaEventUseCase

    @Before
    fun setup() {

        // Init all the mocks
        MockKAnnotations.init(this)

        // Init the test subject
        targetUseCase = DownloadMediaEventUseCase(mockRepository)
    }

    @Test
    fun givenUseCase_whenGetDownloadUri_thenCheckResult() = runTest {

        // Prepare
        val mockUri: Uri = mockk()
        coEvery { mockRepository.getMediaDownloadUrl(any()) } returns mockUri

        // Act
        val result: Uri = targetUseCase(123)

        // Assert
        coVerify { mockRepository.getMediaDownloadUrl(123) }
        result shouldBe mockUri
    }
}
