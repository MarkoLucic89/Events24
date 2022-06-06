package com.markolucic.cubes.events24.ui.adapter.events_adapter.item_model

import android.content.Intent
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.RvItemEventsSmallBinding
import com.markolucic.cubes.events24.ui.activity.EventsDetailActivity
import com.markolucic.cubes.events24.ui.adapter.events_adapter.EventsAdapter
import com.squareup.picasso.Picasso

class ItemEventSmall(
    private val event: Event
    ): ItemEvent {


    override fun getType(): Int = 1


    override fun bind(holder: EventsAdapter.EViewHolder) {

        val binding = holder.binding as RvItemEventsSmallBinding

        binding.textViewTitle.text = event.title
        binding.textViewDate.text = event.date
        Picasso.get().load(event.imageSmall).into(binding.imageView)

        holder.itemView.setOnClickListener { view ->
            val startDetailIntent = Intent(view.context, EventsDetailActivity::class.java)
            startDetailIntent.putExtra("id", event.id)
            view.context.startActivity(startDetailIntent)
        }
    }
}