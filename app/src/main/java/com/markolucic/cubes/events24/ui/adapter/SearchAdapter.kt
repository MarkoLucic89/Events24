package com.markolucic.cubes.events24.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.RvItemEventsSearchBinding
import com.squareup.picasso.Picasso

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

     var events: ArrayList<Event> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        return SearchViewHolder(RvItemEventsSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindEvent(events[position])
    }

    override fun getItemCount(): Int = events.size

    class SearchViewHolder(
        private val binding: RvItemEventsSearchBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindEvent(event: Event) {
            binding.textViewTitle.text = event.title
            binding.textViewDate.text = event.date
            Picasso.get().load(event.imageBig).into(binding.imageView)
        }

    }
}