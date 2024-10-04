package com.edsondev26.folkloripedia.ui.category_detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.FragmentDanceBinding
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import com.squareup.picasso.Picasso

class CategoryDetailFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bindingDance = FragmentDanceBinding.bind(view)

    fun renderDance(danceInfo: DanceDetailModel, onItemSelected: (DanceDetailModel) -> Unit) {
        Picasso.get().load(danceInfo.img).into(bindingDance.ivDance)
        bindingDance.tvDanceName.text = danceInfo.name
        bindingDance.tvDanceRegion.text = danceInfo.region
        bindingDance.tvDanceDescription.text = danceInfo.description.replace("||", "\n\n")
        bindingDance.tvDanceInstruments.text = danceInfo.instruments
        bindingDance.tvDanceVestment.text = danceInfo.vestment
        bindingDance.tvDanceYear.text = danceInfo.year
    }
}