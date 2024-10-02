package com.edsondev26.folkloripedia.ui.article

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
import androidx.recyclerview.widget.GridLayoutManager
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityArticleDetailBinding
import com.edsondev26.folkloripedia.databinding.ActivityMainBinding
import com.edsondev26.folkloripedia.domain.model.ArticleItemModel
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo.Diablada
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo.Morenada
import com.edsondev26.folkloripedia.domain.model.ArticleItemInfo.Tinkus
import com.edsondev26.folkloripedia.ui.article.adapter.ArticleAdapter
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    private val articleDetailViewModel by viewModels<ArticleDetailViewModel>()

    private lateinit var articleAdapter: ArticleAdapter

    private val args: ArticleDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
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
        articleAdapter = ArticleAdapter(onItemSelected = {
            val type: ArticleItemModel = when (it) {
                Morenada -> ArticleItemModel.Morenada
                Diablada -> ArticleItemModel.Diablada
                Tinkus -> ArticleItemModel.Tinkus
            }
        })
        binding.rvListedArticles.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = articleAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleDetailViewModel.articleItems.collect {
                    articleAdapter.updateArticleList(it)
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