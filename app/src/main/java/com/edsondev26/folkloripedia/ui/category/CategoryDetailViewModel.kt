package com.edsondev26.folkloripedia.ui.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.CategoryRepository
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(private val categoryRepository: CategoryRepository):
    ViewModel() {
    private var _categoryItems = MutableStateFlow<List<CategoryItemInfo>>(emptyList())
    val categoryItems: StateFlow<List<CategoryItemInfo>> = _categoryItems

    private var _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
//        fetchCategoryItemsFromFirestore()
        fetchCategoryItems()
    }

    private fun fetchCategoryItems() {
        _isLoading.value = true
        viewModelScope.launch {
            categoryRepository.getCategoryItems().collect { items ->
                _categoryItems.value = items
                _isLoading.value = false
            }
        }
    }

//    private fun fetchCategoryItemsFromFirestore() {
//        val db = FirebaseFirestore.getInstance()
//
//        _isLoading.value = true
//
//        db.collection("Dances")
//            .get()
//            .addOnSuccessListener { result ->
//                val danceList = result.map { document ->
//                    val img = document.getString("Image") ?: ""
//                    val name = document.getString("Name") ?: ""
//                    val region = document.getString("Region") ?: ""
//                    val year = document.getString("Year_Origin")?: ""
//
//                    Log.d("FirebaseFirestore", "Nombre: $name, img: $img")
//
//                    CategoryItemInfo(img, name, region, year)
//                }
//
//                _categoryItems.value = danceList
//                _isLoading.value = false // Finish the loading
//                Log.d("LoadingState - OK", _isLoading.value.toString())
//            }
//            .addOnFailureListener { e ->
//                Log.e("FirebaseError", "Error fetching data", e)
//                _isLoading.value = false // Finish the loading on error
//                Log.d("LoadingState - ERROR", _isLoading.value.toString())
//            }
//    }
}