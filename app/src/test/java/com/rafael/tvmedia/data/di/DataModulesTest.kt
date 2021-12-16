package com.rafael.tvmedia.data.di

import io.kotest.matchers.collections.shouldContainExactly
import org.junit.Test

class DataModulesTest {

    @Test
    fun givenModules_whenListing_thenCheckValues() {

        // Assert
        dataModules shouldContainExactly arrayOf(apiModule, repositoryModule)

    }

}
