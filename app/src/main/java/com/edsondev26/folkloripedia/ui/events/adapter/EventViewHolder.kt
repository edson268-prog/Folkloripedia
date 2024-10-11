package com.edsondev26.folkloripedia.ui.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.ItemEventBinding
import com.edsondev26.folkloripedia.domain.model.EventModel
import com.squareup.picasso.Picasso

class EventViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemEventBinding.bind(view)

    fun renderEvent(event: EventModel) {
        Picasso.get().load(event.img).into(binding.ivEvent)
        binding.tvEventName.text = event.name
        binding.tvEventInfo.text = event.description
    }
}