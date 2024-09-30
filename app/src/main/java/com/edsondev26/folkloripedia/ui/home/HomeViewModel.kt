package com.edsondev26.folkloripedia.ui.home

import androidx.lifecycle.ViewModel
import com.edsondev26.folkloripedia.data.providers.HomeProvider
import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class HomeViewModel @Inject constructor(homeProvider: HomeProvider):ViewModel() {
    private var _home = MutableStateFlow<List<HomeArticleItemInfo>>(emptyList())
    val home:StateFlow<List<HomeArticleItemInfo>> = _home

    init {
        _home.value = homeProvider.getArticles()
    }
}