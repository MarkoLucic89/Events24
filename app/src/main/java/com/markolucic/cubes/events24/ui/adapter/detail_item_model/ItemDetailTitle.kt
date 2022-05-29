package com.markolucic.cubes.events24.ui.adapter.detail_item_model

import com.markolucic.cubes.events24.databinding.RvItemDetailsEventsTitleBinding
import com.markolucic.cubes.events24.ui.adapter.EventDetailAdapter

class ItemDetailTitle(val title: String) : ItemDetail {

    override fun getType(): Int = 1

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding: RvItemDetailsEventsTitleBinding =
            holder.binding as RvItemDetailsEventsTitleBinding

        binding.textViewTitle.setText(title)

    }
}