package com.edsondev26.folkloripedia.ui.events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.domain.model.EventModel

class EventAdapter (private var eventList: List<EventModel> = emptyList()):
    RecyclerView.Adapter<EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.renderEvent(eventList[position])
    }

    fun updateEventList(list: List<EventModel>) {
        eventList = list
        notifyDataSetChanged()
    }
}