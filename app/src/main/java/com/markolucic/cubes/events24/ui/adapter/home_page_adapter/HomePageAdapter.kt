package com.markolucic.cubes.events24.ui.adapter.home_page_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.data.model.Author
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.RvItemHorizontalRvBinding
import com.markolucic.cubes.events24.ui.adapter.home_page_adapter.item_model.*

class HomePageAdapter(val context: Context) :
    RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder>() {

    private val itemList: ArrayList<ItemHome> = arrayListOf()

    private val topEventsList = arrayListOf<Event>()
    private val concertList = arrayListOf<Event>()
    private val theatreList = arrayListOf<Event>()
    private val sportList = arrayListOf<Event>()
    private val recentEventsList = arrayListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {

        val binding = RvItemHorizontalRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomePageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        itemList[position].bind(holder)
    }

    fun updateItemList(events: ArrayList<Event>, authors: ArrayList<Author>) {

        regroupEventsByItemType(events)

        if (topEventsList.isNotEmpty()) {
            itemList.add(ItemHomeBig(context, topEventsList))
        }

        if (concertList.isNotEmpty()) {
            itemList.add(ItemHomeBig(context, concertList))
        }

        if (theatreList.isNotEmpty()) {
            itemList.add(ItemHomeMedium(context, theatreList))
        }

        if (sportList.isNotEmpty()) {
            itemList.add(ItemHomeBig(context, sportList))
        }

        if (recentEventsList.isNotEmpty()) {
            itemList.add(ItemHomeSmall(context, recentEventsList))
        }

        if (authors.isNotEmpty()) {
            itemList.add(ItemHomeAuthor(context, authors))
        }

        notifyDataSetChanged()
    }

    private fun regroupEventsByItemType(events: ArrayList<Event>) {

        clearLists()

        topEventsList.addAll(events)
        topEventsList.sortByDescending { it.clickCounter }

        for (event: Event in events) {
            when {
                event.type.equals("Koncert", true) -> {
                    concertList.add(event)
                }
                event.type.equals("Sport", true) -> {
                    sportList.add(event)
                }
                event.type.equals("Pozoriste", true) -> {
                    theatreList.add(event)
                }
                else -> {

                }
            }
        }

        recentEventsList.addAll(events)
        recentEventsList.sortByDescending { it.dateOfCreation }

    }

    private fun clearLists() {
        topEventsList.clear()
        concertList.clear()
        sportList.clear()
        theatreList.clear()
        recentEventsList.clear()
    }


    override fun getItemCount(): Int = itemList.size

    class HomePageViewHolder(val binding: RvItemHorizontalRvBinding) :
        RecyclerView.ViewHolder(binding.root)
}