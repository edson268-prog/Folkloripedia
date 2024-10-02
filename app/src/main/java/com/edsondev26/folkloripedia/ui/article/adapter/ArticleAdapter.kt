package com.edsondev26.folkloripedia.ui.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo

class ArticleAdapter(private var articlesSelectedList: List<ArticleItemInfo> = emptyList(),
                     private val onItemSelected:(ArticleItemInfo) ->Unit):
    RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_selected, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.render(articlesSelectedList[position], onItemSelected)
    }

    override fun getItemCount(): Int {
        return articlesSelectedList.size
    }

    fun updateArticleList(list: List<ArticleItemInfo>) {
        articlesSelectedList = list
        notifyDataSetChanged()
    }
}