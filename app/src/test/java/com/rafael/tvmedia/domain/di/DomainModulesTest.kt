package com.rafael.tvmedia.domain.di

import io.kotest.matchers.collections.shouldContainExactly
import org.junit.Test

class DomainModulesTest {

    @Test
    fun givenModules_whenListing_thenCheckValues() {

        // Assert
        domainModules shouldContainExactly arrayOf(useCaseModule)

    }
}
