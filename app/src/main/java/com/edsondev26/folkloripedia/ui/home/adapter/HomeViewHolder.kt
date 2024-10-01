package com.edsondev26.folkloripedia.ui.home.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ItemArticleHomeBinding
import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemArticleHomeBinding.bind(view)

    fun render(articleInfo: HomeArticleItemInfo, onItemSelected: (HomeArticleItemInfo) -> Unit) {
        val context = binding.tvArticleName.context
        binding.ivArticle.setImageResource(articleInfo.img)
        binding.tvArticleName.text = context.getString(articleInfo.name)
        binding.tvArticleCount.text = "Este articulo contiene ${context.getString(articleInfo.name)} elementos"

        binding.ArticleInfo.setOnClickListener {
            startClickAnimation(
                binding.ivArticle,
                openNewView = { onItemSelected(articleInfo) })
        }
    }

    private fun startClickAnimation(view: View, openNewView: () -> Unit) {
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.scaling_image)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                openNewView()
            }

            override fun onAnimationRepeat(animation: Animation?) { }
        })
        view.startAnimation(animation)
    }
}
