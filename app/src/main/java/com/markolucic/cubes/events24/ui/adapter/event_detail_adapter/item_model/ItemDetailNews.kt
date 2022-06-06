package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.item_model

import com.markolucic.cubes.events24.data.model.News
import com.markolucic.cubes.events24.databinding.RvItemDetailsEventsNewsBinding
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventDetailAdapter
import com.squareup.picasso.Picasso

class ItemDetailNews(private val news: News): ItemDetail {

    override fun getType(): Int = 5

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding = holder.binding as RvItemDetailsEventsNewsBinding

        binding.textViewTitle.text = news.title
        binding.textViewDate.text = news.date
        binding.textViewDetails.text = news.description
        Picasso.get().load(news.imageUrl).into(binding.imageView)
    }
}