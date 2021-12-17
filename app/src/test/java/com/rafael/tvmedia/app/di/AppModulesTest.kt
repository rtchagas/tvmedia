package com.rafael.tvmedia.app.di

import io.kotest.matchers.collections.shouldContainExactly
import org.junit.Test

class AppModulesTest {

    @Test
    fun givenModules_whenListing_thenCheckValues() {
        // Assert
        appModules shouldContainExactly arrayOf(viewModelModule)
    }
}
