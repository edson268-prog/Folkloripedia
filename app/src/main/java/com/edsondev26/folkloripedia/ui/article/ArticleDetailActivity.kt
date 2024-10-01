package com.edsondev26.folkloripedia.ui.article

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavArgs
import androidx.navigation.navArgs
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityArticleDetailBinding
import com.google.firebase.firestore.FirebaseFirestore

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding
    private val articleDetailViewModel by viewModels<ArticleDetailViewModel>()

    private val args: ArticleDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_article_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i("FOLKLORIPEDIA", "The selected value is ${args.type}")
//        searchDanceByName("Morenada")
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