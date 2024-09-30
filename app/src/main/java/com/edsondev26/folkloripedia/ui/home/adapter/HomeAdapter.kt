package com.edsondev26.folkloripedia.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo

class HomeAdapter(private var articlesItemList: List<HomeArticleItemInfo> = emptyList(),
    private val onItemSelected:(HomeArticleItemInfo) ->Unit):
    RecyclerView.Adapter<HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_home, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return articlesItemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.render(articlesItemList[position], onItemSelected)
    }

    fun updateList(list: List<HomeArticleItemInfo>) {
        articlesItemList = list
        notifyDataSetChanged()
    }
}