package com.markolucic.cubes.events24.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.markolucic.cubes.events24.data.model.Author
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.FragmentHomeBinding
import com.markolucic.cubes.events24.ui.adapter.HomeAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var eventList: ArrayList<Event>
    private lateinit var authorList: ArrayList<Author>
    private lateinit var homeAdapter: HomeAdapter

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        db = FirebaseFirestore.getInstance()

        eventList = arrayListOf()
        authorList = arrayListOf()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        loadData()

    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            homeAdapter = HomeAdapter()
//            homeAdapter.eventList = eventList
            adapter = homeAdapter
        }
    }

    private fun loadData() {


        db.collection("events")
            .get()
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {

                    eventList.clear()

                    for (document: QueryDocumentSnapshot in it.result) {
                        eventList.add(Event(document.id, document.data))
                    }

//                    homeAdapter.eventList = eventList

                    db.collection("authors")
                        .get()
                        .addOnCompleteListener {
                            authorList.clear()

                            for (document: QueryDocumentSnapshot in it.result) {
                                authorList.add(Author(document.data))
                            }

                            homeAdapter.updateLists(eventList, authorList)
                        }



                } else {

                }
            }
    }


    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}