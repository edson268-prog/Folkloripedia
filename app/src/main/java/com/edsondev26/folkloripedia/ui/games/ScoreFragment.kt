package com.edsondev26.folkloripedia.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListeners()
    }

    private fun initListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeQuiz()
        }

        binding.fabReturn.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun initUI() {
        var score = args.score
        val finalScore = String.format("%.2f", score)
        binding.tvScore.text = finalScore
        when(score){
            in 0.00..50.00 ->{
                binding.tvResult.text = getString(R.string.result_low)
                binding.tvDescription.text = getString(R.string.message_low)
            }
            in 50.01..80.00 ->{
                binding.tvResult.text = getString(R.string.result_good)
                binding.tvDescription.text = getString(R.string.message_good)
            }
            in 80.01..100.00 ->{
                binding.tvResult.text = getString(R.string.result_excellent)
                binding.tvDescription.text = getString(R.string.message_excellent)
            }
            else -> {
                binding.tvScore.text = getString(R.string.error)
                binding.tvResult.text = getString(R.string.error)
                binding.tvDescription.text = getString(R.string.error)
            }
        }
    }

    private fun closeQuiz() {
        requireActivity().finish()
    }
}