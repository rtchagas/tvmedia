package com.rafael.tvmedia.domain.usecase

import com.rafael.tvmedia.data.repository.TvGuideRepository

class GetMediaEventsUseCase(private val repository: TvGuideRepository) {

    suspend operator fun invoke() = repository.getMediaEvents()

}
