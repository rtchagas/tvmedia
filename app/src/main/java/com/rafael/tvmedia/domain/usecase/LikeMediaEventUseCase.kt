package com.rafael.tvmedia.domain.usecase

import com.rafael.tvmedia.data.repository.TvMediaRepository

/**
 * Following clean architecture principles, this class is responsible
 * for the single business logic related to the "Like Media Event" use case.
 *
 * It must be completely disconnected from the view and might access
 * as many repositories as necessary to complete its tasks.
 *
 * Changes in presentation and data layer must not affect this user case.
 *
 * No LiveData should be exposed here.
 */
class LikeMediaEventUseCase(private val repository: TvMediaRepository) {

    suspend operator fun invoke(id: Long): Boolean =
        repository.likeMedia(id)

}
