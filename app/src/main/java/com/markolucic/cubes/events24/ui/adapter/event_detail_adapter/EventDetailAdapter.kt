package com.markolucic.cubes.events24.ui.adapter.event_detail_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.data.model.News
import com.markolucic.cubes.events24.databinding.*
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.item_model.*

class EventDetailAdapter(context: Context, event: Event) :
    RecyclerView.Adapter<EventDetailAdapter.EventViewHolder>() {

    private val items: ArrayList<ItemDetail> = ArrayList()

    init {

        //HEADER

        items.add(ItemDetailHeader(context, event))

        if (event.tickets.isNotEmpty()) {

            //TITLE TICKETS

            items.add(ItemDetailTitle(context.getString(R.string.menu_tickets)))

            //TICKETS

            for (ticket in event.tickets) {
                items.add(ItemDetailTicket(ticket))
            }

        }

        if (event.pictures.isNotEmpty()) {

            //TITLE PICTURES

            items.add(ItemDetailTitle(context.getString(R.string.pictures_and_video)))

            //PICTURES

            items.add(ItemDetailPictures(event.pictures))

        }

        if (event.aboutArtist.description != "" && event.aboutArtist.imageUrl != "") {

            //TITLE ABOUT AUTHOR

            items.add(ItemDetailTitle(context.getString(R.string.about_artist)))

            //ABOUT ARTIST

            items.add(ItemDetailAboutArtist(event.aboutArtist))
        }

        if (event.newsList.isNotEmpty()) {

            //TITLE NEWS

            items.add(ItemDetailTitle(context.getString(R.string.news)))

            //NEWS

            for (news: News in event.newsList) {
                items.add(ItemDetailNews(news))
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = when (viewType) {
            0 -> RvItemDetailsEventsHeaderBinding.inflate(inflater, parent, false)
            1 -> RvItemDetailsEventsTitleBinding.inflate(inflater, parent, false)
            2 -> RvItemDetailsTicketsBinding.inflate(inflater, parent, false)
            3 -> RvItemDetailsEventsPicturesBinding.inflate(inflater, parent, false)
            4 -> RvItemDetailsEventAboutArtistBinding.inflate(inflater, parent, false)
            5 -> RvItemDetailsEventsNewsBinding.inflate(inflater, parent, false)
            else -> RvItemDetailsEventsTitleBinding.inflate(inflater, parent, false)
        }

        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        items[position].bind(holder)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getType()

    class EventViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)


}

