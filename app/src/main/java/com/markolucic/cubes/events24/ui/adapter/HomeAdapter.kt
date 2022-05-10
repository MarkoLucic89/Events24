package com.markolucic.cubes.events24.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Author
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.RvItemHomePageBinding
import com.markolucic.cubes.events24.databinding.RvItemLastHomepageBinding
import kotlin.collections.ArrayList

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val topEventsList = arrayListOf<Event>()
    private val concertList = arrayListOf<Event>()
    private val sportList = arrayListOf<Event>()
    private val theatreList = arrayListOf<Event>()
    private val recentEventsList = arrayListOf<Event>()

    var eventList = arrayListOf<Event>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var authorList = arrayListOf<Author>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    private fun regroupEventsByItemType() {

        topEventsList.addAll(eventList)
        topEventsList.sortByDescending { it.clickCounter }

        for (event: Event in eventList) {
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

        recentEventsList.addAll(eventList)
        recentEventsList.sortByDescending { it.dateOfCreation }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                HomeViewHolder(
                    RvItemHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            1 -> {
                HomeViewHolder(
                    RvItemHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            2 -> {
                HomeViewHolder(
                    RvItemHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            3 -> {
                HomeViewHolder(
                    RvItemHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            4 -> {
                HomeViewHolder(
                    RvItemHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            5 -> {
                HomeViewHolder(
                    RvItemHomePageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            6 -> {
                LastItemViewHolder(
                    RvItemLastHomepageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                throw IllegalArgumentException("Invalid ViewType Provided")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> {
                val bigItemHolder = holder as HomeViewHolder
                bigItemHolder.bindEvent(topEventsList, position)
            }
            1 -> {
                val bigItemHolder = holder as HomeViewHolder
                bigItemHolder.bindEvent(concertList, position)
            }
            2 -> {
                val mediumItemHolder = holder as HomeViewHolder
                mediumItemHolder.bindEvent(theatreList, position)
            }
            3 -> {
                val mediumItemHolder = holder as HomeViewHolder
                mediumItemHolder.bindEvent(sportList, position)
            }
            4 -> {
                val smallItemHolder = holder as HomeViewHolder
                smallItemHolder.bindEvent(recentEventsList, position)
            }
            5 -> {
                val smallItemHolder = holder as HomeViewHolder
                smallItemHolder.bindAuthor(eventList,authorList)
            }
        }
    }

    override fun getItemCount(): Int = 7

    override fun getItemViewType(position: Int): Int {
        if (position == 6) {
            return 6
        }
        return 0
    }

    fun updateLists(eventList: ArrayList<Event>, authorList: ArrayList<Author>) {
        this.eventList = eventList
        this.authorList = authorList

        regroupEventsByItemType()
    }

    class LastItemViewHolder(private val binding: RvItemLastHomepageBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    class HomeViewHolder(private val binding: RvItemHomePageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context


        fun bindEvent(eventList: ArrayList<Event>, position: Int) {

            when (position) {

                0 -> {
                    binding.imageViewPlay.setColorFilter(context.resources.getColor(R.color.pink))
                    binding.textViewType.text = context.getString(R.string.text_top_events)
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        adapter = EventsAdapter(eventList, itemType = ItemType.TYPE_BIG)
                    }
                }

                1 -> {
                    binding.imageViewPlay.setColorFilter(context.resources.getColor(R.color.purple))
                    binding.textViewType.text = context.getString(R.string.text_koncerti)
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        adapter = EventsAdapter(eventList, itemType = ItemType.TYPE_BIG)
                    }
                }

                2 -> {
                    binding.imageViewPlay.setColorFilter(context.resources.getColor(R.color.purple))
                    binding.textViewType.text = context.getString(R.string.text_pozoriste)
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        adapter = EventsAdapter(eventList, itemType = ItemType.TYPE_MEDIUM)
                    }
                }

                3 -> {
                    binding.imageViewPlay.setColorFilter(context.resources.getColor(R.color.purple))
                    binding.textViewType.text = context.getString(R.string.text_sport)
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        adapter = EventsAdapter(eventList, itemType = ItemType.TYPE_BIG)
                    }
                }

                4 -> {
                    binding.imageViewPlay.setColorFilter(context.resources.getColor(R.color.purple))
                    binding.textViewType.text = context.getString(R.string.text_najnoviji_dogadjaji)
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        adapter = EventsAdapter(eventList, itemType = ItemType.TYPE_SMALL)
                    }
                }


            }

        }

        fun bindAuthor(eventList: ArrayList<Event>, authorList: ArrayList<Author>) {
            binding.imageViewPlay.setColorFilter(context.resources.getColor(R.color.yellow))
            binding.textViewType.text = context.getString(R.string.text_izvodjaci)
            binding.recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = EventsAdapter(eventList, authorList, ItemType.TYPE_AUTHOR)
            }
        }
    }


}