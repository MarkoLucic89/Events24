package com.markolucic.cubes.events24.ui.adapter.events_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.*
import com.markolucic.cubes.events24.ui.adapter.events_adapter.item_model.*

class EventsAdapter(
    private val context: Context?,
    private val events: ArrayList<Event> = arrayListOf(),
    private val resourceId: Int,
) : RecyclerView.Adapter<EventsAdapter.EViewHolder>() {

    private var listener: OnEventsClickListener? = null
    private var itemList: ArrayList<ItemEvent> = ArrayList()

    init {
        initItemList(events)
    }

    private fun initItemList(events: java.util.ArrayList<Event>?) {

        when (resourceId) {
            R.layout.rv_item_events_big -> {

                for (event: Event in events!!) {
                    itemList.add(ItemEventBig(event))
                }

            }
            R.layout.rv_item_events_small -> {

                for (event: Event in events!!) {
                    itemList.add(ItemEventSmall(event))
                }

            }
            R.layout.rv_item_events_medium -> {

                for (event: Event in events!!) {
                    itemList.add(ItemEventMedium(event))
                }

            }
            R.layout.rv_item_events_grid -> {

                for (event: Event in events!!) {
                    itemList.add(ItemEventGrid(event))
                }

            }
            else -> {

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EViewHolder {

        val binding: ViewBinding
        val inflater = LayoutInflater.from(parent.context)

        binding = when (viewType) {

            0 -> RvItemEventsBigBinding.inflate(inflater, parent, false)
            1 -> RvItemEventsSmallBinding.inflate(inflater, parent, false)
            2 -> RvItemEventsMediumBinding.inflate(inflater, parent, false)
            3 -> RvItemEventsGridBinding.inflate(inflater, parent, false)
            else -> RvItemEventsSmallBinding.inflate(inflater, parent, false)

        }

        return EViewHolder(binding)

    }

    override fun onBindViewHolder(holder: EViewHolder, position: Int) {

        itemList[position].bind(holder)

    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int = itemList[position].getType()

     fun updateList(list: ArrayList<Event>) {
        itemList.clear()
        initItemList(list)
        notifyDataSetChanged()
    }

    fun setListener(listener: OnEventsClickListener?) {
        this.listener = listener
    }

    class EViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)


    interface OnEventsClickListener {
        fun onEventClick(event: Event?)
    }

}