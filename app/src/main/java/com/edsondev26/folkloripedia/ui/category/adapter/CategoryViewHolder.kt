package com.edsondev26.folkloripedia.ui.category.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.ItemCategoryBinding
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo

class CategoryViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoryBinding.bind(view)

    fun render(categoryItems: CategoryItemInfo, onItemSelected: (CategoryItemInfo) -> Unit) {
        val context = binding.tvListedName.context
        binding.ivListedArticle.setImageResource(categoryItems.img)
        binding.tvListedName.text = context.getString(categoryItems.name)

        binding.articleListed.setOnClickListener {
            onItemSelected(categoryItems)
        }
    }
}