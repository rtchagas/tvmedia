package com.rafael.tvmedia.app.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.view.activity.ThemeableActivity
import com.rafael.tvmedia.app.view.adapter.NowPlayingAdapter
import com.rafael.tvmedia.app.viewmodel.NowPlayingViewModel
import com.rafael.tvmedia.app.viewmodel.Result
import com.rafael.tvmedia.databinding.FragmentNowPlayingBinding
import com.rafael.tvmedia.model.MediaEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class NowPlayingFragment :
    BaseFragment<FragmentNowPlayingBinding>(FragmentNowPlayingBinding::inflate) {

    private val viewModel: NowPlayingViewModel by viewModel()

    private val rvAdapter = NowPlayingAdapter(::handleMediaEventClick)
        .apply { stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is ThemeableActivity) {
            Timber.w("Activity should implement ${ThemeableActivity::class.simpleName}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        setupToolbar()
        initializeUi()
        registerObservers()

        // Fetch the media events
        refresh()
    }

    private fun initializeUi() {

        with(binding.rvPlayingEvents) {
            layoutManager = GridLayoutManager(
                requireContext(),
                resources.getInteger(R.integer.now_playing_span_count)
            )
            setHasFixedSize(true)
            adapter = rvAdapter
        }

        binding.swipePlayingEvents.setOnRefreshListener { refresh() }
    }

    private fun refresh() {
        // Refresh the items
        viewModel.refreshMediaEvents()
    }

    private fun registerObservers() {
        viewModel.observeMediaEvents().observe(viewLifecycleOwner, ::handleMediaEventsResult)
    }

    private fun handleMediaEventClick(event: MediaEvent, sharedView: View) {

        val directions = NowPlayingFragmentDirections
            .actionFragmentNowPlayingToFragmentMediaView(event)

        // Set the shared transition element
        val extras = FragmentNavigatorExtras(sharedView to "media_view_thumb")

        findNavController().navigate(directions, extras)
    }

    private fun handleMediaEventsResult(result: Result<List<MediaEvent>>) {

        // Cancel the loading state
        binding.swipePlayingEvents.isRefreshing = false

        when (result) {
            is Result.Success -> handleLoadingSuccess(result.data)
            is Result.Error -> handleLoadingError(result.error)
        }
    }

    private fun handleLoadingSuccess(events: List<MediaEvent>) {
        Timber.i("Loaded ${events.size} media items")
        rvAdapter.update(events)
    }

    private fun handleLoadingError(error: Throwable) {
        Timber.w("Could not load media events", error)
        //TODO: Replace by a snack bar
        Toast.makeText(requireContext(), "Could not load media events", Toast.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        // We might be running tests where the root activity is not themeable
        (requireActivity() as? ThemeableActivity)?.resetToolbar()
    }
}
