package com.edsondev26.folkloripedia.ui.category_detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityCategoryDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryDetailFragmentBinding

    private val viewModel: CategoryDetailFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryDetailFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
    }

    private fun initUI() {
        initRedirect()
    }

    private fun initRedirect() {
        val categoryType = intent.extras?.getString("CATEGORY_TYPE").orEmpty()
        val itemId = intent.extras?.getString("ITEM_ID").orEmpty()

        when (categoryType) {
            "DA" -> navigateToFragment<DanceFragment>(itemId)
            "AR" -> navigateToFragment<ArtFragment>(itemId)
            else -> throw IllegalArgumentException("Unknown category type")
        }
    }

    inline fun <reified T : Fragment> navigateToFragment(itemId: String) {
        val fragment = T::class.java.newInstance().apply {
            arguments = Bundle().apply {
                putString("ITEM_ID", itemId)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}