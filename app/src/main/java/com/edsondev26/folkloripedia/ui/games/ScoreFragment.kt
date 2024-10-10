package com.edsondev26.folkloripedia.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
        binding.tvScore.text = "Score: $finalScore %"
    }

    private fun closeQuiz() {
        requireActivity().finish()
    }
}