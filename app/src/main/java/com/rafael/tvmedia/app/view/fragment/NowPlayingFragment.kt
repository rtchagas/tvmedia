package com.rafael.tvmedia.app.view.fragment

import android.os.Bundle
import android.view.View
import com.rafael.tvmedia.app.viewmodel.NowPlayingViewModel
import com.rafael.tvmedia.databinding.FragmentNowPlayingBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

class NowPlayingFragment :
    BaseFragment<FragmentNowPlayingBinding>(FragmentNowPlayingBinding::inflate) {

    private val viewModel: NowPlayingViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Now playing created")
    }
}
