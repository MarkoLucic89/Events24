package com.markolucic.cubes.events24.ui.adapter.detail_item_model

import com.markolucic.cubes.events24.data.model.AboutArtist
import com.markolucic.cubes.events24.databinding.RvItemDetailsEventAboutArtistBinding
import com.markolucic.cubes.events24.ui.adapter.EventDetailAdapter
import com.squareup.picasso.Picasso

class ItemDetailAboutArtist(private val aboutArtist: AboutArtist):ItemDetail {
    override fun getType(): Int = 4

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding = holder.binding as RvItemDetailsEventAboutArtistBinding

        binding.textViewDetails.text = aboutArtist.description
        Picasso.get().load(aboutArtist.imageUrl).into(binding.imageView)
    }
}