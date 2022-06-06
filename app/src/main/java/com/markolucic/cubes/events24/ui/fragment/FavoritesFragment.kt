package com.markolucic.cubes.events24.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.markolucic.cubes.events24.R
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.data.room.AppDatabase
import com.markolucic.cubes.events24.databinding.FragmentFavoritesBinding
import com.markolucic.cubes.events24.ui.adapter.events_adapter.EventsAdapter
import kotlinx.coroutines.*

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    private lateinit var favouriteEvents: ArrayList<Event>

    private lateinit var mAdapter: EventsAdapter

    private lateinit var selectedType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        favouriteEvents = arrayListOf()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setListeners()

        selectedType = "All"


        GlobalScope.launch(Dispatchers.IO) {

            favouriteEvents =
                AppDatabase.getInstance(context)?.eventDAO()?.getAll() as ArrayList<Event>

            withContext(Dispatchers.Main) {
                mAdapter.updateList(favouriteEvents)
            }

        }


    }


    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            mAdapter = EventsAdapter(context, favouriteEvents, R.layout.rv_item_events_grid)
            adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    private fun setListeners() {
        binding.buttonAll.setOnClickListener { filterList("All") }
        binding.buttonConcerts.setOnClickListener { filterList("Koncert") }
        binding.buttonTheatres.setOnClickListener { filterList("Pozoriste") }
        binding.buttonSport.setOnClickListener { filterList("Sport") }
    }

    private fun setButtonColors() {

        binding.buttonAll.setBackgroundResource(R.drawable.background_button_purple_dark_2)
        binding.buttonConcerts.setBackgroundResource(R.drawable.background_button_purple_dark_2)
        binding.buttonTheatres.setBackgroundResource(R.drawable.background_button_purple_dark_2)
        binding.buttonSport.setBackgroundResource(R.drawable.background_button_purple_dark_2)

        when (selectedType) {
            "All" -> binding.buttonAll.setBackgroundResource(R.drawable.background_button_purple)
            "Koncert" -> binding.buttonConcerts.setBackgroundResource(R.drawable.background_button_purple)
            "Pozoriste" -> binding.buttonTheatres.setBackgroundResource(R.drawable.background_button_purple)
            "Sport" -> binding.buttonSport.setBackgroundResource(R.drawable.background_button_purple)
        }
    }

    private fun filterList(type: String) {
        selectedType = type
        updateUi()
        setButtonColors()
    }

    private fun updateUi() {

        GlobalScope.launch(Dispatchers.IO) {

            favouriteEvents = when {

                selectedType.equals("All", ignoreCase = true) -> {
                    AppDatabase.getInstance(context)?.eventDAO()?.getAll()
                            as ArrayList<Event>
                }
                else -> {
                    AppDatabase.getInstance(context)?.eventDAO()?.getAllByType(selectedType)
                            as ArrayList<Event>
                }

            }

            withContext(Dispatchers.Main){
                mAdapter.updateList(favouriteEvents)
            }

        }



    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}