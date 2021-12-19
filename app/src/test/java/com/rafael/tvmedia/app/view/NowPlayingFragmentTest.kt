package com.rafael.tvmedia.app.view

import androidx.navigation.fragment.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.util.FakeDataUtil
import com.rafael.tvmedia.app.util.launchFragment
import com.rafael.tvmedia.app.view.adapter.NowPlayingAdapter
import com.rafael.tvmedia.app.view.fragment.NowPlayingFragment
import com.rafael.tvmedia.data.repository.TvMediaRepository
import com.rafael.tvmedia.robolectric.RoboTest
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class NowPlayingFragmentTest : RoboTest() {

    @MockK
    private lateinit var mockRepository: TvMediaRepository

    private lateinit var mockModule: Module

    @Before
    fun setup() {

        // Initialize the mocks
        MockKAnnotations.init(this)

        // RoleManager default behavior
        coEvery {
            mockRepository.getMediaEvents()
        } returns FakeDataUtil.getFakeMediaItems(targetContext)

        // Initialize the Koin modules
        mockModule = module {
            single { mockRepository }
        }

        loadKoinModules(mockModule)
    }

    @After
    fun tearDown() {
        unmockkAll()
        unloadKoinModules(mockModule)
    }

    @Test
    fun givenFragment_whenHasMediaEvents_thenCheckEventsSize() {

        // Prepare
        val scenario = launchFragment<NowPlayingFragment>(
            destination = R.id.fragment_now_playing
        )

        // Assert
        scenario.onFragment {
            it.binding.rvPlayingEvents.adapter?.itemCount shouldBe 3
        }
    }

    @Test
    fun givenFragment_whenHasMediaEvents_thenCheckHasEpisode() {

        // Prepare
        val scenario = launchFragment<NowPlayingFragment>(
            destination = R.id.fragment_now_playing
        )

        // Assert
        scenario.onFragment {

            // Scroll fails if the predicate does not match
            onView(withId(R.id.rv_playing_events))
                .perform(
                    RecyclerViewActions.scrollTo<NowPlayingAdapter.MediaItemHolder>(
                        allOf(
                            hasDescendant(withText("Gladiatorerna del 3")),
                            hasDescendant(withText("Season 11 - Episode 3")),
                        )
                    )
                )
        }
    }

    @Test
    fun givenFragment_whenHasMediaEvents_thenCheckHasClip() {

        // Prepare
        val scenario = launchFragment<NowPlayingFragment>(
            destination = R.id.fragment_now_playing
        )

        // Assert
        scenario.onFragment {

            // Scroll fails if the predicate does not match
            onView(withId(R.id.rv_playing_events))
                .perform(
                    RecyclerViewActions.scrollTo<NowPlayingAdapter.MediaItemHolder>(
                        hasDescendant(withText("Vädret Jönköpings län"))
                    )
                )
        }
    }

    @Test
    fun givenFragment_whenHasMediaEvents_whenClickOnItem_thenCheckNavigation() {

        // Prepare
        val scenario = launchFragment<NowPlayingFragment>(
            destination = R.id.fragment_now_playing
        )

        // Assert
        scenario.onFragment {

            // Click on the item
            onView(withId(R.id.rv_playing_events))
                .perform(
                    RecyclerViewActions.actionOnItem<NowPlayingAdapter.MediaItemHolder>(
                        hasDescendant(withText("Gladiatorerna del 3")),
                        ViewActions.click()
                    )
                )
        }

        // Assert
        scenario.onFragment {
            // Check that we navigated
            it.findNavController().currentDestination?.id shouldBe R.id.fragment_media_view
        }
    }
}
