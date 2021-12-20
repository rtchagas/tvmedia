package com.rafael.tvmedia.domain.usecase

import android.net.Uri
import com.rafael.tvmedia.data.repository.TvMediaRepository

/**
 * Following clean architecture principles, this class is responsible
 * for the single business logic related to the "Download Media Event" use case.
 *
 * It must be completely disconnected from the view and might access
 * as many repositories as necessary to complete its tasks.
 *
 * Changes in presentation and data layer must not affect this user case.
 *
 * No LiveData should be exposed here.
 */
class DownloadMediaEventUseCase(private val repository: TvMediaRepository) {

    suspend operator fun invoke(id: Long): Uri =
        repository.getMediaDownloadUrl(id)

}
