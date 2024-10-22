package com.edsondev26.folkloripedia.ui.category_detail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.FragmentMusicBinding
import com.edsondev26.folkloripedia.domain.model.MusicDetailModel
import com.squareup.picasso.Picasso

class MusicDetailFragmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bindingArt = FragmentMusicBinding.bind(view)

    fun renderMusic(musicInfo: MusicDetailModel, onItemSelected: (MusicDetailModel) -> Unit) {
        Picasso.get().load(musicInfo.img).into(bindingArt.ivMusic)
        bindingArt.tvMusicName.text = musicInfo.name
        bindingArt.tvMusicOrigin.text = musicInfo.origin
        bindingArt.tvMusicCategory.text = musicInfo.category
        bindingArt.tvMusicMaterial.text = musicInfo.material
        bindingArt.tvMusicDescription.text = musicInfo.description.replace("||", "\n\n")
        if (musicInfo.sound.isEmpty()) {
            bindingArt.fabPlayMusic.visibility = View.GONE
        } else {
            bindingArt.fabPlayMusic.visibility = View.VISIBLE
        }
    }
}