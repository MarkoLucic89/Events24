package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.item_model

import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventDetailAdapter

interface ItemDetail {

    fun getType():Int

    fun bind(holder: EventDetailAdapter.EventViewHolder)

}