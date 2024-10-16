package com.edsondev26.folkloripedia.ui.main

import android.app.Dialog
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityMainBinding
import com.edsondev26.folkloripedia.utils.LanguageUtils
import com.edsondev26.folkloripedia.utils.StatusBarUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Force to always work with light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        StatusBarUtils.setStatusBarColor(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        initDefaultLanguage()
        initNavigation()
        initListeners()
    }

    private fun initDefaultLanguage() {
        val currentLanguage = LanguageUtils.getSavedLanguage(this@MainActivity)
        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun initListeners() {
        binding.btnLanguages.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val dialog = Dialog(this)

        dialog.setContentView(R.layout.dialog_languages)

        val btnSpanish = dialog.findViewById<Button>(R.id.mbSpanish)
        btnSpanish.setOnClickListener {
            dialog.dismiss()
            setLocale("es")
        }

        val btnEnglish = dialog.findViewById<Button>(R.id.mbEnglish)
        btnEnglish.setOnClickListener {
            dialog.dismiss()
            setLocale("en")
        }

        dialog.show()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()

        LanguageUtils.setLanguage(this, languageCode)
    }

    private fun initNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}