package com.rafael.tvmedia.app.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
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
        if (activity !is ThemeableActivity) {
            Timber.w("Activity should implement ${ThemeableActivity::class.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
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
            ivMediaViewThumb.load(url) {
                listener { _, _ -> startPostponedEnterTransition() }
            }

            // Title
            tvMediaViewTitle.text = event.title

            // Season Info
            if (event.type == MediaEventType.EPISODE) {
                bindSeasonInfo(event)
            }

            // Action button based on future or past event
            if (!event.isPastEvent()) {
                btnMediaViewAction.text = getString(R.string.all_remind_me)
                btnMediaViewAction.icon =
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_alarm_24, null)
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

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_media_thumbnail)
    }

    private fun toDateString(dateInMillis: Long): String {
        val formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
        return formatter.format(Date(dateInMillis))
    }

    private fun setupToolbar() {
        // We might be running tests where the root activity is not themeable
        (activity as? ThemeableActivity)?.setTransparentToolbar()
    }
}
