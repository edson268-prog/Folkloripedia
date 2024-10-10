package com.edsondev26.folkloripedia.ui.games

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.ActivityRouletteBinding
import com.edsondev26.folkloripedia.databinding.DialogCuriosityBinding
import com.edsondev26.folkloripedia.domain.model.CuriosityModel
import com.edsondev26.folkloripedia.ui.core.listeners.OnSwipeTouchListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

@AndroidEntryPoint
class RouletteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouletteBinding
    private lateinit var bindingDialog: DialogCuriosityBinding

    private var curiosityDialog: Dialog? = null
    private val rouletteViewModel by viewModels<RouletteViewModel>()
    private var isAnimationInProgress = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRouletteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingDialog = DialogCuriosityBinding.inflate(layoutInflater)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
//        binding.ivRoulette.setOnClickListener {
//            spinRoulette(true)
//        }
        binding.ivRoulette.setOnTouchListener(object : OnSwipeTouchListener(this@RouletteActivity) {
            override fun onSwipeRight() {
                spinRoulette(true)
            }

            override fun onSwipeLeft() {
                spinRoulette(false)
            }
        })
    }

    // START ANIMATION
    private fun spinRoulette(toRight: Boolean) {
        Log.i("spinRoulette", "spinRoulette")
        if (isAnimationInProgress) {
            return
        }
        isAnimationInProgress = true
        Log.i("spinRoulette", "loadCuriosity")
        loadCuriosity()

        val random = Random.Default
        var degrees = random.nextInt(1440) + 540

        if (!toRight) {
            degrees *= -1
        }

        val animator =
            ObjectAnimator.ofFloat(binding.ivRoulette, View.ROTATION, 0f, degrees.toFloat())

        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        animator.doOnEnd {
            itemAnimation()
        }
        animator.start()
    }

    private fun itemAnimation() {
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        slideUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                binding.ivGlobe.isVisible = true
            }

            override fun onAnimationEnd(p0: Animation?) {
                growItem()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })

        binding.ivGlobe.startAnimation(slideUpAnimation)
    }

    private fun growItem() {
        val growAnimation = AnimationUtils.loadAnimation(this, R.anim.grow_element)
        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                binding.ivGlobe.isVisible = false
                isAnimationInProgress = false
                curiosityDialog?.show()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })

        binding.ivGlobe.startAnimation(growAnimation)
    }
    // END ANIMATION

    // LOAD AND SHOW CURIOSITY
    private fun loadCuriosity() {
        curiosityDialog = createDialog()
        val random = Random.Default
        val randomId = random.nextInt(1..2).toString()
        rouletteViewModel.fetchCuriosityById(randomId)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                rouletteViewModel.curiosityItem.collect { curiosityItem ->
                    curiosityItem?.let {
                        updateDialogWithCuriosity(curiosityDialog!!, it)
                    }
                }
            }
        }
    }


    private fun createDialog(): Dialog {
        val dialogCuriosity = Dialog(this)
        dialogCuriosity.setContentView(R.layout.dialog_curiosity)

        val btnClose = dialogCuriosity.findViewById<Button>(R.id.btnCloseCuriosity)
        btnClose.setOnClickListener {
            dialogCuriosity.dismiss()
            finish()
        }

        val btnRetry = dialogCuriosity.findViewById<Button>(R.id.btnRetryCuriosity)
        btnRetry.setOnClickListener {
            dialogCuriosity.dismiss()
            btnClose.visibility = View.GONE
            recreate()
        }

        dialogCuriosity.window?.let { window ->
            val displayMetrics = resources.displayMetrics
            val width = (displayMetrics.widthPixels * 0.8).toInt()
            val height = (displayMetrics.heightPixels * 0.8).toInt()
            window.setLayout(width, height)
        }

        dialogCuriosity.setCancelable(false)
        dialogCuriosity.setCanceledOnTouchOutside(false)

        return dialogCuriosity
    }

    private fun updateDialogWithCuriosity(dialog: Dialog, curiosity: CuriosityModel) {
        val ivCuriosity = dialog.findViewById<ImageView>(R.id.ivCuriosity)
        val tvDescription = dialog.findViewById<TextView>(R.id.tvDescription)
        val preview = dialog.findViewById<ConstraintLayout>(R.id.previewCuriosity)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.pbItemInfo)

        Picasso.get().load(curiosity.img).into(ivCuriosity)
        tvDescription.text = curiosity.description.replace("||", "\n\n")
        progressBar.visibility = View.GONE
        preview.visibility = View.VISIBLE
    }
    // END
}