package com.markolucic.cubes.events24.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.data.model.Author
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.RvItemEventsBigBinding
import com.markolucic.cubes.events24.databinding.RvItemEventsMediumBinding
import com.markolucic.cubes.events24.databinding.RvItemEventsSmallBinding
import com.markolucic.cubes.events24.ui.activity.EventsDetailActivity
import com.squareup.picasso.Picasso
class EventsAdapter(
    private var eventList: List<Event>,
    private var authorList: List<Author> = arrayListOf(),
    private val itemType: ItemType,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (itemType) {
            ItemType.TYPE_BIG -> {
                EventsBigViewHolder(RvItemEventsBigBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
            ItemType.TYPE_MEDIUM -> {
                EventsMediumViewHolder(RvItemEventsMediumBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
            ItemType.TYPE_SMALL -> {
                EventsSmallViewHolder(RvItemEventsSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
            ItemType.TYPE_AUTHOR -> {
                EventsSmallViewHolder(RvItemEventsSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false))
            }


        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (itemType) {
            ItemType.TYPE_BIG -> {
                val event = eventList[position]
                val eventsViewHolder = holder as EventsBigViewHolder
                eventsViewHolder.bindEvent(event)
            }
            ItemType.TYPE_MEDIUM -> {
                val event = eventList[position]
                val eventsViewHolder = holder as EventsMediumViewHolder
                eventsViewHolder.bindEvent(event)
            }
            ItemType.TYPE_SMALL -> {
                val event = eventList[position]
                val eventsViewHolder = holder as EventsSmallViewHolder
                eventsViewHolder.bindEvent(event)
            }
            ItemType.TYPE_AUTHOR -> {
                val author = authorList[position]
                val eventsViewHolder = holder as EventsSmallViewHolder
                eventsViewHolder.bindAuthor(author)
            }

        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EventsDetailActivity::class.java)
            intent.putExtra("id", eventList[position].id)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return if (itemType == ItemType.TYPE_AUTHOR) {
            authorList.size
        } else {
            eventList.size
        }
    }

    private inner class EventsMediumViewHolder(private val binding: RvItemEventsMediumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindEvent(event: Event) {

            binding.textViewTitle.text = event.title
            binding.textViewDate.text = event.date
            Picasso.get().load(event.imageSmall).into(binding.imageView)
        }

    }

    private inner class EventsSmallViewHolder(private val binding: RvItemEventsSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindEvent(event: Event) {

            binding.textViewTitle.text = event.title
            binding.textViewDate.text = event.date
            Picasso.get().load(event.imageSmall).into(binding.imageView)
        }

        fun bindAuthor(author: Author) {
            binding.textViewTitle.text = "${author.name} ${author.surname}"
            binding.textViewDate.visibility = View.GONE
            Picasso.get().load(author.image).into(binding.imageView)
        }
    }

    private class EventsBigViewHolder(private val binding: RvItemEventsBigBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindEvent(event: Event) {

            binding.textViewTitle.text = event.title
            binding.textViewDate.text = event.date
            Picasso.get().load(event.imageBig).into(binding.imageView)

        }

    }

}




