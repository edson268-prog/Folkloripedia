package com.edsondev26.folkloripedia.ui.category_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.databinding.FragmentDanceBinding
import com.edsondev26.folkloripedia.domain.model.DanceDetailModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DanceFragment : Fragment() {
    private val danceViewModel by viewModels<CategoryDetailFragmentViewModel>()

    private var _binding: FragmentDanceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDanceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val itemId = requireArguments().getString("ITEM_ID", "")
        danceViewModel.fetchDanceById(itemId)
        initUI()
        getDanceItem()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun initUI() {
        getDanceItem()
        initUIState()
    }

    private fun getDanceItem() {
        lifecycleScope.launch {
            danceViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.pbItemInfo.visibility = View.VISIBLE
                    binding.dancePreview.visibility = View.GONE
                } else {
                    binding.pbItemInfo.visibility = View.GONE
                    binding.dancePreview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                danceViewModel.danceItem.collect { danceItem ->
                    danceItem?.let {
                        renderDance(it)
                    }
                }
            }
        }
    }

    private fun renderDance(danceItem: DanceDetailModel) {
        binding.tvDanceName.text = danceItem.name
        binding.tvDanceDescription.text = danceItem.description
    }
}