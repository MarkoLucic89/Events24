package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.databinding.RvItemPictureBinding
import com.squareup.picasso.Picasso

class EventPicturesAdapter(private val list: ArrayList<String>)
    : RecyclerView.Adapter<EventPicturesAdapter.EventPicturesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventPicturesViewHolder {
        val binding: RvItemPictureBinding = RvItemPictureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false

        )
        return EventPicturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventPicturesViewHolder, position: Int) {

        Picasso.get().load(list[position]).into(holder.binding.imageViewPicture)

    }

    override fun getItemCount(): Int = list.size

    class EventPicturesViewHolder(val binding: RvItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
