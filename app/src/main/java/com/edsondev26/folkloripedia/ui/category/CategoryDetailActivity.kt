package com.edsondev26.folkloripedia.ui.category

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryDetailBinding
    private val categoryViewModel by viewModels<CategoryDetailViewModel>()

    private lateinit var categoryAdapter: CategoryAdapter

    private val args: CategoryDetailActivityArgs by navArgs()

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
//        searchDanceByName("Morenada")

        initUI()
    }

    private fun initUI() {
        initListedArticles()
        initUIState()
    }

    private fun initListedArticles() {
        categoryAdapter = CategoryAdapter(onItemSelected = {
            val type: CategoryModel = when (it) {
                Morenada -> CategoryModel.Morenada
                Diablada -> CategoryModel.Diablada
                Tinkus -> CategoryModel.Tinkus
            }
        })
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


    fun searchDanceByName(danceName: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Dances")
            .whereEqualTo("Name", danceName)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
//                        val danceData = document.data
//                        val name = danceData["Name"] as String
//                        val description = danceData["Description"] as String
//
//                        Log.d("FirebaseFirestore", "Nombre: $name, Descripción: $description")
                        Log.d("FirebaseFirestore", "Los resultados encontrados son ${documents}")
                    }
                } else {
                    Log.d("FirebaseFirestore", "No se encontraron resultados para: $danceName")
                }
            }
            .addOnFailureListener { exception ->
                // Manejo de errores
                Log.e("FirebaseFirestore", "Error al buscar documentos: ", exception)
            }
    }
}