package com.markolucic.cubes.events24.ui.adapter.detail_item_model

import com.markolucic.cubes.events24.ui.adapter.EventDetailAdapter

interface ItemDetail {

    fun getType():Int

    fun bind(holder: EventDetailAdapter.EventViewHolder)

}