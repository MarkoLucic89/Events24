package com.markolucic.cubes.events24.ui.adapter.home_page_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markolucic.cubes.events24.data.model.Author
import com.markolucic.cubes.events24.databinding.RvItemEventsAuthorBinding
import com.squareup.picasso.Picasso

class AuthorsAdapter(
    val context: Context,
    private val authors: ArrayList<Author>,
) :
    RecyclerView.Adapter<AuthorsAdapter.AuthorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        return AuthorViewHolder(RvItemEventsAuthorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {

        holder.binding.textViewTitle.text = authors[position].name
        Picasso.get().load(authors[position].image).into(holder.binding.imageView)

    }

    override fun getItemCount(): Int = authors.size

    class AuthorViewHolder(val binding: RvItemEventsAuthorBinding) :
        RecyclerView.ViewHolder(binding.root)
}