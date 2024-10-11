package com.edsondev26.folkloripedia.ui.events.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ItemEventBinding
import com.edsondev26.folkloripedia.domain.model.EventModel
import com.squareup.picasso.Picasso

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemEventBinding.bind(view)

    fun renderEvent(event: EventModel) {
        val dayLabel = itemView.context.getString(R.string.day)
        Picasso.get().load(event.img).into(binding.ivEvent)
        binding.tvEventName.text = "${event.name} - $dayLabel: ${if (event.day == 0) "Variable" else event.day}"
        binding.tvEventInfo.text = event.description
    }
}