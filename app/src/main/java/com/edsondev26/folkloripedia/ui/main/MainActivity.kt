package com.edsondev26.folkloripedia.ui.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityMainBinding
import com.edsondev26.folkloripedia.ui.home.adapter.LanguageSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var languages: Array<String>
    private lateinit var flags: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUi()
    }

    private fun initUi() {
        initNavigation()
        initLanguageConfig()
    }

    private fun initLanguageConfig() {
        languages = resources.getStringArray(R.array.languages)
        flags = intArrayOf(R.drawable.ic_en_uk, R.drawable.ic_es_spain)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val flagAdapter = LanguageSpinnerAdapter(this, languages, flags)
        binding.spLanguages.adapter = flagAdapter

        binding.spLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {

                val selectedLanguageCode = if (position == 0) "en" else "es"

                val currentLanguage = getSelectedLanguage()

                if (selectedLanguageCode != currentLanguage) {
                    Toast.makeText(this@MainActivity, "Selected: ${languages[position]}", Toast.LENGTH_SHORT).show()
                    setLocale(selectedLanguageCode)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()

        val sharedPref = getSharedPreferences("LanguagePref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("SelectedLanguage", languageCode)
            apply()
        }
    }

    private fun getSelectedLanguage(): String {
        val sharedPref = getSharedPreferences("LanguagePref", Context.MODE_PRIVATE)
        return sharedPref.getString("SelectedLanguage", "en") ?: "en"
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}