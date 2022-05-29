package com.markolucic.cubes.events24.ui.adapter.detail_item_model

import com.markolucic.cubes.events24.data.model.Ticket
import com.markolucic.cubes.events24.databinding.RvItemDetailsTicketsBinding
import com.markolucic.cubes.events24.ui.adapter.EventDetailAdapter

class ItemDetailTicket(val ticket: Ticket) : ItemDetail {

    override fun getType(): Int = 2

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding = holder.binding as RvItemDetailsTicketsBinding

        binding.textViewTitle.text = ticket.title
        binding.textViewPrice.text = ticket.price
    }
}