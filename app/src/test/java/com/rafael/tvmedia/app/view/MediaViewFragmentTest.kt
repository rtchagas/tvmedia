package com.rafael.tvmedia.app.view

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.util.FakeDataUtil
import com.rafael.tvmedia.app.util.launchFragmentWithArgs
import com.rafael.tvmedia.app.view.fragment.MediaViewFragment
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.robolectric.RoboTest
import io.kotest.matchers.shouldBe
import org.junit.Test

class MediaViewFragmentTest : RoboTest() {

    @Test
    fun givenFragment_whenArgumentIsEpisode_thenCheckCorrectUiValues() {

        // Prepare

        // Choose an episode to be our test data
        val mediaEvent: MediaEvent =
            FakeDataUtil.getFakeMediaItems(targetContext).component2()

        // Act
        val scenario = launchFragmentWithArgs<MediaViewFragment>(
            destination = R.id.fragment_media_view,
            navArgs = bundleOf(Pair("arg_media_event", mediaEvent))
        )

        // Assert
        scenario.onFragment {

            with(it.binding) {

                // Title
                tvMediaViewTitle.text shouldBe mediaEvent.title

                // Season Info
                tvMediaViewSeasonInfo.text shouldBe targetContext.getString(
                    R.string.now_playing_item_season_info,
                    mediaEvent.season,
                    mediaEvent.episode
                )

                // Description
                tvMediaViewDescription.text shouldBe mediaEvent.description

                // Broadcast Time
                tvMediaViewBroadcastValue.text shouldBe "December 17, 2021, 10:00 AM"

                // Published on
                tvMediaViewPublishedValue.text shouldBe "December 16, 2021, 12:05 PM"

                // Region availability
                tvMediaViewRegionValue.text shouldBe mediaEvent.regionAvailability
            }
        }
    }

    @Test
    fun givenFragment_whenArgumentIsClip_thenCheckCorrectUiValues() {

        // Prepare

        // Choose a clip to be our test data
        val mediaEvent: MediaEvent =
            FakeDataUtil.getFakeMediaItems(targetContext).last()

        // Act
        val scenario = launchFragmentWithArgs<MediaViewFragment>(
            destination = R.id.fragment_media_view,
            navArgs = bundleOf(Pair("arg_media_event", mediaEvent))
        )

        // Assert
        scenario.onFragment {

            with(it.binding) {

                // Title
                tvMediaViewTitle.text shouldBe mediaEvent.title

                // Season Info
                tvMediaViewSeasonInfo.isVisible shouldBe false

                // Description
                tvMediaViewDescription.text shouldBe targetContext.getString(
                    R.string.media_view_description_not_available
                )

                // Broadcast Time
                tvMediaViewBroadcastValue.text shouldBe "December 16, 2021, 4:36 PM"

                // Published on
                tvMediaViewPublishedValue.text shouldBe "December 16, 2021, 4:36 PM"

                // Region availability
                tvMediaViewRegionValue.text shouldBe mediaEvent.regionAvailability
            }
        }
    }

    @Test
    fun givenFragment_whenArgumentIsFuture_thenCheckActionButton() {

        // Prepare

        // Choose a clip to be our test data
        val mediaEvent: MediaEvent =
            FakeDataUtil.getFakeMediaItems(targetContext).first()

        // Act
        val scenario = launchFragmentWithArgs<MediaViewFragment>(
            destination = R.id.fragment_media_view,
            navArgs = bundleOf(Pair("arg_media_event", mediaEvent))
        )

        // Assert
        scenario.onFragment {

            with(it.binding) {
                // Action button
                btnMediaViewAction.text shouldBe targetContext.getString(R.string.all_remind_me)
            }
        }
    }
}
