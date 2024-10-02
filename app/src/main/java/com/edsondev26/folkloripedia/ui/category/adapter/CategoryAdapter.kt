package com.edsondev26.folkloripedia.ui.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo

class CategoryAdapter(private var categoriesList: List<CategoryItemInfo> = emptyList(),
                      private val onItemSelected:(CategoryItemInfo) ->Unit):
    RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.render(categoriesList[position], onItemSelected)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    fun updateCategoriesList(list: List<CategoryItemInfo>) {
        categoriesList = list
        notifyDataSetChanged()
    }
}