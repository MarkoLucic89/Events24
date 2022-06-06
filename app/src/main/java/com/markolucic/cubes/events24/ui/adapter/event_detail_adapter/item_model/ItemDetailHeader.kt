package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.item_model

import android.content.Context
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.data.room.AppDatabase
import com.markolucic.cubes.events24.databinding.RvItemDetailsEventsHeaderBinding
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventDetailAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemDetailHeader(private val context: Context, private val event: Event) : ItemDetail {

    override fun getType(): Int = 0

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding: RvItemDetailsEventsHeaderBinding =
            holder.binding as RvItemDetailsEventsHeaderBinding

        binding.textViewType.text = event.type
        binding.textViewTitle.text = event.title
        binding.textViewPlace.text = event.location
        binding.textViewDate.text = event.date
        Picasso.get().load(event.imageBig).into(binding.imageView)

        var favoriteEvent: Event? = null

        GlobalScope.launch {
            favoriteEvent =  AppDatabase.getInstance(context)?.eventDAO()?.getEvent(event.id)

            when (favoriteEvent) {
                null -> binding.imageViewAddToFavorites.setImageResource(R.drawable.ic_favorite_border)
                else -> binding.imageViewAddToFavorites.setImageResource(R.drawable.ic_favorite)
            }
        }

        binding.imageViewAddToFavorites.setOnClickListener {

            when (favoriteEvent) {
                null -> {
                    GlobalScope.launch {
                        AppDatabase.getInstance(context)!!.eventDAO()?.insert(event)
                        binding.imageViewAddToFavorites.setImageResource(R.drawable.ic_favorite)
                    }

                }
                else -> {

                    GlobalScope.launch {
                        AppDatabase.getInstance(context)!!.eventDAO()?.delete(event)
                        binding.imageViewAddToFavorites.setImageResource(R.drawable.ic_favorite_border)
                    }

                }
            }

        }

    }
}