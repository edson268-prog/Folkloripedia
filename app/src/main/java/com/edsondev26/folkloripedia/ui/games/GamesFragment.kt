package com.edsondev26.folkloripedia.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.edsondev26.folkloripedia.databinding.FragmentGamesBinding
import com.edsondev26.folkloripedia.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
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
                // HomeFragmentDirections was generated automatically -- Add argument on categoryDetailActivity  main_graph
                GamesFragmentDirections.actionGamesFragmentToRouletteActivity()
            )
        }
    }

//    private test_crash() {
//        //Test crash for crashlytics
//        binding.btnException.setOnClickListener {
//            throw RuntimeException("Test Crash for example") // Force a crash
//        }
//    }
}