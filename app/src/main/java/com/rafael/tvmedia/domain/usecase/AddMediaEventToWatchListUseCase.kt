package com.rafael.tvmedia.domain.usecase

import com.rafael.tvmedia.domain.repository.TvMediaRepository

/**
 * Following clean architecture principles, this class is responsible
 * for the single business logic related to the "Add a Media Event to Watch List" use case.
 *
 * It must be completely disconnected from the view and might access
 * as many repositories as necessary to complete its tasks.
 *
 * Changes in presentation and data layer must not affect this user case.
 *
 * No LiveData should be exposed here.
 */
class AddMediaEventToWatchListUseCase(private val repository: TvMediaRepository) {

    suspend operator fun invoke(id: Long): Boolean =
        repository.addMediaToWatchList(id)

}
