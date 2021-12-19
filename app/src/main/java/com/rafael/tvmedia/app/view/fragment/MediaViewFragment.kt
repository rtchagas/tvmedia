package com.rafael.tvmedia.app.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import coil.load
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.view.activity.ThemeableActivity
import com.rafael.tvmedia.app.view.util.ImageResizeUtil
import com.rafael.tvmedia.databinding.FragmentMediaViewBinding
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.model.MediaEventType
import timber.log.Timber
import java.text.DateFormat
import java.util.*

class MediaViewFragment :
    BaseFragment<FragmentMediaViewBinding>(FragmentMediaViewBinding::inflate) {

    private val args: MediaViewFragmentArgs by navArgs()

    private val thumbsMaxHeight by lazy {
        resources.getDimensionPixelSize(R.dimen.media_view_thumbs_max_height)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        require(activity is ThemeableActivity) {
            Timber.e("Activity must implement ${ThemeableActivity::class.simpleName}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initializeUi(args.argMediaEvent)
    }

    private fun bindSeasonInfo(event: MediaEvent) {
        if (event.season > 0) {
            binding.tvMediaViewSeasonInfo.isVisible = true
            binding.tvMediaViewSeasonInfo.text =
                getString(R.string.now_playing_item_season_info, event.season, event.episode)
        }
    }

    private fun initializeUi(event: MediaEvent) {

        with(binding) {

            // Thumbnail via Coil
            val url = ImageResizeUtil.resize(originalUrl = event.image, height = thumbsMaxHeight)
            ivMediaViewThumb.load(url)

            // Title
            tvMediaViewTitle.text = event.title

            // Season Info
            if (event.type == MediaEventType.EPISODE) {
                bindSeasonInfo(event)
            }

            // Description
            val description: String = event.description
                ?: getString(R.string.media_view_description_not_available)
            tvMediaViewDescription.text = description

            // Broadcast time
            tvMediaViewBroadcastValue.text = toDateString(event.broadcastsOn)

            // Published time
            tvMediaViewPublishedValue.text = toDateString(event.publishedOn)

            // Region
            tvMediaViewRegionValue.text = event.regionAvailability
        }
    }

    private fun toDateString(dateInMillis: Long): String {
        val formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
        return formatter.format(Date(dateInMillis))
    }

    private fun setupToolbar() {
        (activity as ThemeableActivity).setTransparentToolbar()
    }
}
