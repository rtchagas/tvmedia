package com.rafael.tvmedia.app.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rafael.tvmedia.R
import com.rafael.tvmedia.app.view.util.ImageResizeUtil
import com.rafael.tvmedia.databinding.ItemMediaEventBinding
import com.rafael.tvmedia.model.MediaEvent
import com.rafael.tvmedia.model.MediaEventType
import java.util.concurrent.TimeUnit

class NowPlayingAdapter : RecyclerView.Adapter<NowPlayingAdapter.MediaItemHolder>() {

    private val mediaItems = mutableListOf<MediaEvent>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemHolder {
        val binding = ItemMediaEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MediaItemHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaItemHolder, position: Int) {
        holder.bind(mediaItems[position])
    }

    override fun getItemCount(): Int =
        mediaItems.size

    override fun getItemId(position: Int): Long =
        mediaItems[position].id

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<MediaEvent>) {
        mediaItems.clear()
        mediaItems.addAll(items)
        notifyDataSetChanged()
    }

    class MediaItemHolder(private val binding: ItemMediaEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        private val thumbsHeight = context.resources
            .getDimensionPixelSize(R.dimen.item_play_event_height)

        fun bind(event: MediaEvent) {

            with(binding) {

                // Thumbnail
                val url = ImageResizeUtil.resize(originalUrl = event.image, height = thumbsHeight)
                ivItemMediaThumb.load(url) { crossfade(true) }

                // Title
                tvItemMediaTitle.text = event.title

                // Season Info
                binding.tvItemMediaSeasonInfo.isVisible = false
                if (event.type == MediaEventType.EPISODE) {
                    bindSeasonInfo(event)
                }

                // Broadcast Time
                bindBroadcastTime(event)
            }
        }

        private fun bindBroadcastTime(event: MediaEvent) {

            val nowMillis = System.currentTimeMillis()
            val diff = event.broadcastsOn - nowMillis

            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)

            if (diff > 0) {
                bindBroadcastTimePast(hours, minutes)
            } else {
                bindBroadcastTimeFuture(hours, minutes)
            }
        }

        private fun bindBroadcastTimeFuture(hours: Long, minutes: Long) {
            if (hours > 0) {
                binding.tvItemMediaBroadcastTime.text = context.getString(
                    R.string.now_playing_item_broadcast_future_hours,
                    hours, minutes
                )
            } else {
                binding.tvItemMediaBroadcastTime.text = context.getString(
                    R.string.now_playing_item_broadcast_future_minutes,
                    minutes
                )
            }
        }

        private fun bindBroadcastTimePast(hours: Long, minutes: Long) {
            if (hours > 0) {
                binding.tvItemMediaBroadcastTime.text = context.getString(
                    R.string.now_playing_item_broadcast_past_hours,
                    hours, minutes
                )
            } else {
                binding.tvItemMediaBroadcastTime.text = context.getString(
                    R.string.now_playing_item_broadcast_past_minutes,
                    minutes
                )
            }
        }

        private fun bindSeasonInfo(event: MediaEvent) {
            if (event.season > 0) {
                binding.tvItemMediaSeasonInfo.isVisible = true
                binding.tvItemMediaSeasonInfo.text = context
                    .getString(R.string.now_playing_item_season_info, event.season, event.episode)
            }
        }
    }
}
