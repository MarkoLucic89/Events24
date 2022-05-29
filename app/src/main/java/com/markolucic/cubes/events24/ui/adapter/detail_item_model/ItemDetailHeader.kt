package com.markolucic.cubes.events24.ui.adapter.detail_item_model

import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.RvItemDetailsEventsHeaderBinding
import com.markolucic.cubes.events24.ui.adapter.EventDetailAdapter
import com.squareup.picasso.Picasso

class ItemDetailHeader(private val event:Event): ItemDetail {

    override fun getType(): Int = 0

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding:RvItemDetailsEventsHeaderBinding = holder.binding as RvItemDetailsEventsHeaderBinding

        binding.textViewType.text = event.type
        binding.textViewTitle.text = event.title
        binding.textViewPlace.text = event.location
        binding.textViewDate.text = event.date
        Picasso.get().load(event.imageBig).into(binding.imageView)

    }
}