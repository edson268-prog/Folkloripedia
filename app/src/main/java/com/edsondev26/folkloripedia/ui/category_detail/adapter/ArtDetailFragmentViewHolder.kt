package com.edsondev26.folkloripedia.ui.category_detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.FragmentArtBinding
import com.edsondev26.folkloripedia.domain.model.ArtDetailModel
import com.squareup.picasso.Picasso

class ArtDetailFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bindingArt = FragmentArtBinding.bind(view)

    fun renderArt(artInfo: ArtDetailModel, onItemSelected: (ArtDetailModel) -> Unit) {
        Picasso.get().load(artInfo.img).into(bindingArt.ivArt)
        bindingArt.tvArtName.text = artInfo.name
        bindingArt.tvArtAuthor.text = artInfo.author
        bindingArt.tvArtDescription.text = artInfo.description
        bindingArt.tvArtMaterial.text = artInfo.material
    }
}