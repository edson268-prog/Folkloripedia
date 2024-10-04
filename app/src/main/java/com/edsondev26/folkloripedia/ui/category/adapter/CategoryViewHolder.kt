package com.edsondev26.folkloripedia.ui.category.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.ItemCategoryBinding
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.squareup.picasso.Picasso

class CategoryViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoryBinding.bind(view)

    fun render(category: CategoryItemModel, onItemSelected: (CategoryItemModel) -> Unit) {
        Picasso.get().load(category.img).into(binding.ivListedArticle)
        binding.tvListedName.text = category.name
        binding.tvInfo.text = category.info
        itemView.setOnClickListener { onItemSelected(category) }
    }
}