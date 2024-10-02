package com.edsondev26.folkloripedia.ui.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityCategoryDetailBinding
import com.edsondev26.folkloripedia.domain.model.CategoryItemInfo.*
import com.edsondev26.folkloripedia.domain.model.CategoryModel
import androidx.recyclerview.widget.GridLayoutManager
import com.edsondev26.folkloripedia.ui.category.adapter.CategoryAdapter
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryDetailBinding
    private val categoryViewModel by viewModels<CategoryDetailViewModel>()

    private lateinit var categoryAdapter: CategoryAdapter

    private val args: CategoryDetailActivityArgs by navArgs()

    private val viewModel: CategoryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryAdapter = CategoryAdapter { category ->
            // Aquí va la lógica de lo que sucede cuando seleccionas un ítem
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i("FOLKLORIPEDIA", "The selected value is ${args.type}")
//        searchDanceByName("Morenada")

        initUI()
    }

    private fun initUI() {
        initListedArticles()
        initUIState()
    }

    private fun initListedArticles() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.isLoading.collect { isLoading ->
                Log.d("LoadingState", "isLoading: $isLoading")
                if (isLoading) {
                    binding.pbCategoryItems.visibility = View.VISIBLE
                    binding.rvListedArticles.visibility = View.GONE
                } else {
                    binding.pbCategoryItems.visibility = View.GONE
                    binding.rvListedArticles.visibility = View.VISIBLE
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.categoryItems.collect { categories ->
                categoryAdapter.updateCategoriesList(categories)
            }
        }

        binding.rvListedArticles.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = categoryAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categoryItems.collect {
                    categoryAdapter.updateCategoriesList(it)
                }
            }
        }
    }

//    private fun loadingState() {
//        binding.pbHoroscopeDetail.isVisible = true
//    }
//
//    private fun errorState() {
//        binding.pbHoroscopeDetail.isVisible = false
//    }
//
//    private fun successState(state: HoroscopeDetailState.Success) {
//        binding.pbHoroscopeDetail.isVisible = false
//        binding.tvTitle.text = state.sign
//        binding.tvBody.text = state.prediction
//
//        binding.ivDetail.setImageResource(image)
//    }
}