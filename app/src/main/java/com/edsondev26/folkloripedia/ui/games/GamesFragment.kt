package com.edsondev26.folkloripedia.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.edsondev26.folkloripedia.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.mbRouletteGame.setOnClickListener {
            findNavController().navigate(
                GamesFragmentDirections.actionGamesFragmentToRouletteActivity()
            )
        }

        binding.mbQuizGame.setOnClickListener {
            findNavController().navigate(
                GamesFragmentDirections.actionGamesFragmentToQuizActivity()
            )
        }
    }
}