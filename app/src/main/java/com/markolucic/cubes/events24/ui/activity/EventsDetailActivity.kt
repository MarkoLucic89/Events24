package com.markolucic.cubes.events24.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.markolucic.cubes.events24.data.model.Event
import com.markolucic.cubes.events24.databinding.ActivityEventsDetailBinding
import com.markolucic.cubes.events24.ui.adapter.event_detail_adapter.EventDetailAdapter

class EventsDetailActivity : BasicActivity() {

    private lateinit var binging: ActivityEventsDetailBinding

    private lateinit var mEventID: String
    private lateinit var mEvent:Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binging = ActivityEventsDetailBinding.inflate(layoutInflater)
        setContentView(binging.root)

        initEvent()
        setListeners()


    }

    private fun initEvent() {
        mEventID = intent.getStringExtra("id").toString()

        FirebaseFirestore.getInstance()
            .collection("events")
            .document(mEventID)
            .get()
            .addOnSuccessListener {

                mEvent = Event(it.id, it.data!!)

                binging.recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@EventsDetailActivity)
                    adapter = EventDetailAdapter(this@EventsDetailActivity, mEvent)
                }

            }
    }

    private fun setListeners() {
        binging.imageViewBack.setOnClickListener { finish() }
        binging.imageViewShare.setOnClickListener { share() }
    }

    private fun share() {

    }
}