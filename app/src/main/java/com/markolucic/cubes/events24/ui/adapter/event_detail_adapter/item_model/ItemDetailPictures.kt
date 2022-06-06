package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.item_model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.databinding.RvItemDetailsEventsPicturesBinding
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventDetailAdapter
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventPicturesAdapter

class ItemDetailPictures(
    private val pictures: ArrayList<String> = arrayListOf(),
) : ItemDetail {

    override fun getType(): Int = 3

    override fun bind(holder: EventDetailAdapter.EventViewHolder) {

        val binding = holder.binding as RvItemDetailsEventsPicturesBinding

        binding.recyclerView.apply {

            layoutManager =
                LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL, false)

            adapter = EventPicturesAdapter(pictures)

        }
    }
}