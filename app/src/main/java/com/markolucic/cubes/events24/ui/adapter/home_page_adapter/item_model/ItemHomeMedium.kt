package com.markolucic.cubes.events24.ui.adapter.home_page_adapter.item_model

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.ui.adapter.events_adapter.EventsAdapter
import com.markolucic.cubes.events24.ui.adapter.home_page_adapter.HomePageAdapter

class ItemHomeMedium(val context: Context, val events:ArrayList<Event>) : ItemHome {

    override fun bind(holder: HomePageAdapter.HomePageViewHolder) {

        holder.binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = EventsAdapter(context, events, R.layout.rv_item_events_medium)
        }

    }
}