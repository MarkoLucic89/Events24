package com.markolucic.cubes.events24.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.FragmentSearchBinding
import com.markolucic.cubes.events24.ui.adapter.events_adapter.EventsAdapter
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentSearchBinding
    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var eventList: ArrayList<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        eventList = arrayListOf()
        firestore = FirebaseFirestore.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchRecyclerView()
        setSearchTextListener()

    }

    private fun setSearchTextListener() {

        binding.editTextSearch.addTextChangedListener(afterTextChanged = {
            searchEvents(it.toString())
        })

    }

    private fun searchEvents(searchTerm: String) {

        val searchList: ArrayList<Event> = arrayListOf()

        if (searchTerm.isEmpty()) {
            updateResults(searchList)
            return
        }

        firestore.collection("events")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    for (document: QueryDocumentSnapshot in it.result) {
                        val event = Event(document.id, document.data)
                        if (event.title.lowercase(Locale.getDefault())
                                .contains(searchTerm.lowercase(Locale.getDefault()))
                        ) {

                            searchList.add(event)

                        }
                    }

                    updateResults(searchList)

                } else {

                }
            }
    }

    private fun updateResults(searchList: ArrayList<Event>) {

        if (searchList.isEmpty()) {
            binding.textViewSearchResults.text = "No results"
        } else {
            binding.textViewSearchResults.text = "${searchList.size} results"
        }

        eventsAdapter.updateList(searchList)
    }

    private fun initSearchRecyclerView() {

        eventsAdapter = EventsAdapter(context, eventList, R.layout.rv_item_events_grid)

        binding.recyclerView.apply {
            adapter = eventsAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}