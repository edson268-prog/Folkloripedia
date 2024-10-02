package com.edsondev26.folkloripedia.ui.article.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.ItemArticleSelectedBinding
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemArticleSelectedBinding.bind(view)

    fun render(articleListed: ArticleItemInfo, onItemSelected: (ArticleItemInfo) -> Unit) {
        val context = binding.tvListedName.context
        binding.ivListedArticle.setImageResource(articleListed.img)
        binding.tvListedName.text = context.getString(articleListed.name)

        binding.articleListed.setOnClickListener {
            onItemSelected(articleListed)
        }
    }
}