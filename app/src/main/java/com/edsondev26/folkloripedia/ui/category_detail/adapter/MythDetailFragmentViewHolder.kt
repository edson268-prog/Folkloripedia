package com.edsondev26.folkloripedia.ui.category_detail.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.FragmentMythBinding
import com.edsondev26.folkloripedia.domain.model.MythDetailModel
import com.squareup.picasso.Picasso

class MythDetailFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bindingArt = FragmentMythBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun renderMyth(artInfo: MythDetailModel, onItemSelected: (MythDetailModel) -> Unit) {
        Picasso.get().load(artInfo.img).into(bindingArt.ivMyth)
        bindingArt.tvMythName.text = "${artInfo.name} | ${artInfo.origin}"
        bindingArt.tvMythTale.text = artInfo.Tale.replace("||", "\n\n")
    }
}