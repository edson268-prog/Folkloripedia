package com.edsondev26.folkloripedia.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.ItemArticleHomeBinding
import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemArticleHomeBinding.bind(view)

    fun render(articleInfo: HomeArticleItemInfo, onItemSelected: (HomeArticleItemInfo) -> Unit) {
        val context = binding.tvArticleName.context
        binding.ivArticle.setImageResource(articleInfo.img)
        binding.tvArticleName.text = context.getString(articleInfo.name)
        binding.tvArticleCount.text = "Este articulo contiene ${context.getString(articleInfo.name)} elementos"

//        binding.ArticleInfo.setOnClickListener {
//            startRotationAnimation(
//                binding.ivHoroscope,
//                newLambda = { onItemSelected(horoscopeInfo) })
//        }
    }
}
