package com.edsondev26.folkloripedia.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityCategoryDetailBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.ui.category.adapter.CategoryAdapter
import com.edsondev26.folkloripedia.ui.category_detail.ArtFragment
import com.edsondev26.folkloripedia.ui.category_detail.CategoryDetailFragmentActivity
import com.edsondev26.folkloripedia.ui.category_detail.DanceFragment
import com.edsondev26.folkloripedia.ui.home.HomeFragmentDirections
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i("FOLKLORIPEDIA", "The selected value is ${args.type}")

        initUI()
    }

    private fun initUI() {
        binding.titleToolbar.tvMainTitle.text = getString(R.string.articles)
        initListedArticles(args.type.toString())
        initUIState()
    }

    private fun initListedArticles(collectionName: String) {
        viewModel.fetchCategoryItems(collectionName)

        // When the element is pressed
        categoryAdapter = CategoryAdapter { selectedItem ->
            val itemId = selectedItem.id
            Toast.makeText(this, "Selected: $itemId", Toast.LENGTH_SHORT).show()
            navigateToCategoryDetail(selectedItem)
        }

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

    private fun navigateToCategoryDetail(category: CategoryItemModel) {
        val intent = Intent(this, CategoryDetailFragmentActivity::class.java)
        intent.putExtra("CATEGORY_TYPE", category.type)
        intent.putExtra("ITEM_ID", category.id)
        startActivity(intent)
    }
}