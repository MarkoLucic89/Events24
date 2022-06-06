package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.item_model

import com.markolucic.cubes.events24.databinding.RvItemDetailsEventsTitleBinding
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventDetailAdapter

class ItemDetailTitle(val title: String) : ItemDetail {

    override fun getType(): Int = 1

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding: RvItemDetailsEventsTitleBinding =
            holder.binding as RvItemDetailsEventsTitleBinding

        binding.textViewTitle.setText(title)

    }
}