package com.rafael.tvmedia.app.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
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
import kotlin.math.abs

class NowPlayingAdapter(private val listener: (MediaEvent, View) -> Unit) :
    RecyclerView.Adapter<NowPlayingAdapter.MediaItemHolder>() {

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
        val mediaEvent = mediaItems[position]
        holder.bind(mediaEvent)
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

    inner class MediaItemHolder(private val binding: ItemMediaEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        private val thumbsHeight = context.resources
            .getDimensionPixelSize(R.dimen.media_view_thumbs_max_height)

        fun bind(event: MediaEvent) {

            with(binding) {

                // Thumbnail via Coil
                val url = ImageResizeUtil.resize(originalUrl = event.image, height = thumbsHeight)
                ivItemMediaThumb.load(url) { crossfade(true) }
                ivItemMediaThumb.transitionName = event.id.toString()

                // Title
                tvItemMediaTitle.text = event.title

                // Season Info
                binding.tvItemMediaSeasonInfo.isVisible = false
                if (event.type == MediaEventType.EPISODE) {
                    bindSeasonInfo(event)
                }

                // Broadcast Time
                bindBroadcastTime(event)

                // Live icon
                ivItemMediaLive.isVisible = event.isLive
                tvItemMediaLive.isVisible = event.isLive

                // Click event
                root.setOnClickListener { listener(event, ivItemMediaThumb) }
            }
        }

        private fun bindBroadcastTime(event: MediaEvent) {

            // Absolute diff
            val diff = abs(event.getTimeToBroadcast())

            val days = TimeUnit.MILLISECONDS.toDays(diff)
            val hours = TimeUnit.MILLISECONDS.toHours(diff) % HOURS_IN_A_DAY
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % MINUTES_IN_AN_HOUR


            bindBroadcastTimeText(event.isPastEvent(), days, hours, minutes)
        }

        private fun bindBroadcastTimeText(isPast: Boolean, days: Long, hours: Long, minutes: Long) {

            val text = StringBuilder()

            if (days > 0) {
                val daysStr = context.resources
                    .getQuantityString(R.plurals.all_days, days.toInt(), days)
                text.append("$daysStr ")
            }

            if (hours > 0) {
                val hoursStr = context.resources
                    .getQuantityString(R.plurals.all_hours, hours.toInt(), hours)
                text.append("$hoursStr ")
            }

            // If less then a day, show the minutes
            if ((days <= 0) && (minutes > 0)) {
                val minutesStr = context.resources
                    .getQuantityString(R.plurals.all_minutes, minutes.toInt(), minutes)
                text.append(minutesStr)
            }

            if (isPast) {
                binding.tvItemMediaBroadcastTime.text =
                    context.getString(R.string.now_playing_item_broadcast_past, text.toString())
            } else {
                binding.tvItemMediaBroadcastTime.text =
                    context.getString(R.string.now_playing_item_broadcast_future, text.toString())
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

    companion object {
        private const val HOURS_IN_A_DAY = 24
        private const val MINUTES_IN_AN_HOUR = 60
    }
}
