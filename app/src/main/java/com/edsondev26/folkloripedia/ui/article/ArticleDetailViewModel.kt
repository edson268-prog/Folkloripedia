package com.edsondev26.folkloripedia.ui.article

import androidx.lifecycle.ViewModel
import com.edsondev26.folkloripedia.data.providers.ArticleItemProvider
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(articleItemProvider: ArticleItemProvider):ViewModel() {
    private var _articleItems = MutableStateFlow<List<ArticleItemInfo>>(emptyList())
    val articleItems: StateFlow<List<ArticleItemInfo>> = _articleItems

    init {
        _articleItems.value = articleItemProvider.getArticleItems()
    }
}