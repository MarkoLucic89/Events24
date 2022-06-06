package com.markolucic.cubes.events24.ui.adapter.events_adapter.item_model

import com.markolucic.cubes.events24.ui.adapter.events_adapter.EventsAdapter

interface ItemEvent {

    fun getType():Int

    fun bind(holder: EventsAdapter.EViewHolder)

}