package com.edsondev26.folkloripedia.ui.games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
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

        val score = args.score
        binding.tvScore.text = "Score: $score"

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToGamesFragment()
        }
    }

    private fun navigateToGamesFragment() {
        findNavController().navigate(R.id.action_scoreFragment_to_gamesFragment)
//        findNavController().navigate(
//            ScoreFragmentDirections.actionScoreFragmentToGamesFragment()
//        )
    }
}