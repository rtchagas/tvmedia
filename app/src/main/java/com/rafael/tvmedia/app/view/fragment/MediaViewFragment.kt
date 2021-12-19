package com.rafael.tvmedia.app.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.view.activity.ThemeableActivity
import com.rafael.tvmedia.app.view.util.ImageResizeUtil
import com.rafael.tvmedia.app.viewmodel.MediaViewViewModel
import com.rafael.tvmedia.databinding.FragmentMediaViewBinding
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.model.MediaEventType
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.DateFormat
import java.util.*


class MediaViewFragment :
    BaseFragment<FragmentMediaViewBinding>(FragmentMediaViewBinding::inflate) {

    private val viewModel: MediaViewViewModel by viewModel()

    private val args: MediaViewFragmentArgs by navArgs()

    private val fadeInAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            .apply { fillAfter = true }
    }

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
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        setupToolbar()

        viewModel.mediaEvent = args.argMediaEvent
        initializeUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_media_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            shareMediaContent()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindSeasonInfo(event: MediaEvent) {
        if (event.season > 0) {
            binding.tvMediaViewSeasonInfo.isVisible = true
            binding.tvMediaViewSeasonInfo.text =
                getString(R.string.now_playing_item_season_info, event.season, event.episode)
        }
    }

    private fun handleLike() = lifecycleScope.launchWhenResumed {
        if (viewModel.like()) {
            toggleLikeState(true)
        }
    }

    private fun handleDislike() = lifecycleScope.launchWhenResumed {
        if (viewModel.dislike()) {
            toggleLikeState(false)
        }
    }

    private fun handleAddToWatchList() = lifecycleScope.launchWhenResumed {
        if (viewModel.addToWatchList()) {
            Snackbar
                .make(requireView(), R.string.media_view_watchlist_success, Snackbar.LENGTH_LONG)
                .setAction(R.string.all_undo) { }
                .show()
        }
    }

    private fun handleDownload() = lifecycleScope.launchWhenResumed {
        if (viewModel.download()) {
            Snackbar.make(requireView(), R.string.media_view_download_success, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun loadMediaThumbnail(event: MediaEvent, imageView: ImageView) {
        val url = ImageResizeUtil.resize(originalUrl = event.image, height = thumbsMaxHeight)
        imageView.load(url) {
            // Only perform the shared transition when the thumbnail is loaded
            listener(
                onSuccess = { _, _ -> startPostponedEnterTransition() },
                onError = { _, ex ->
                    Timber.w("Could not load image for media ${event.id}: $ex")
                    startPostponedEnterTransition()
                }
            )
        }
    }

    private fun initializeUi() {

        val event = viewModel.mediaEvent

        with(binding) {

            // Thumbnail via Coil
            loadMediaThumbnail(event, ivMediaViewThumb)

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

        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            btnMediaViewLike.setOnClickListener { handleLike() }
            btnMediaViewDislike.setOnClickListener { handleDislike() }
            btnMediaViewWatchlist.setOnClickListener { handleAddToWatchList() }
            btnMediaViewDownload.setOnClickListener { handleDownload() }
        }
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_media_thumbnail).apply {
                addListener(object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) {
                        setupPostSharedTransitionViews()
                    }
                })
            }
    }

    private fun setupPostSharedTransitionViews() {
        with(binding) {
            scrimMediaViewThumb.startAnimation(fadeInAnimation)
            tvMediaViewTitle.startAnimation(fadeInAnimation)
        }
    }

    private fun setupToolbar() {
        // We might be running tests where the root activity is not themeable
        (activity as? ThemeableActivity)?.setTransparentToolbar()
    }

    private fun shareMediaContent() {

        val event: MediaEvent = viewModel.mediaEvent

        val playStoreUrl = getString(R.string.app_share_url, event.id.toString())

        val payload = getString(
            R.string.media_view_share_content,
            event.title,
            playStoreUrl
        )

        val sendIntent: Intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, payload)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun toDateString(dateInMillis: Long): String {
        val formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
        return formatter.format(Date(dateInMillis))
    }

    private fun toggleLikeState(isLike: Boolean) = with(binding) {
        if (isLike) {
            btnMediaViewLike.setImageResource(R.drawable.ic_thumb_up_on_24)
            btnMediaViewDislike.setImageResource(R.drawable.ic_thumb_down_24)
        } else {
            btnMediaViewLike.setImageResource(R.drawable.ic_thumb_up_24)
            btnMediaViewDislike.setImageResource(R.drawable.ic_thumb_down_on_24)
        }
    }
}
