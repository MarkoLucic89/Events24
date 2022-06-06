package com.markolucic.cubes.events24.ui.adapter.home_page_adapter.item_model

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.data.model.Author
import com.markolucic.cubes.events24.ui.adapter.home_page_adapter.AuthorsAdapter
import com.markolucic.cubes.events24.ui.adapter.home_page_adapter.HomePageAdapter

class ItemHomeAuthor(
    val context: Context,
    val authors:ArrayList<Author>
) : ItemHome {

    override fun bind(holder: HomePageAdapter.HomePageViewHolder) {

        holder.binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = AuthorsAdapter(context, authors)
        }
    }
}