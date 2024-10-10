package com.edsondev26.folkloripedia.ui.category_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.databinding.FragmentArtBinding
import com.edsondev26.folkloripedia.ui.category_detail.adapter.ArtDetailFragmentViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtFragment : Fragment() {
    private val artViewModel by viewModels<CategoryDetailFragmentViewModel>()

    private var _binding: FragmentArtBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewHolder: ArtDetailFragmentViewHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewHolder = ArtDetailFragmentViewHolder(view)
        val itemId = requireArguments().getString("ITEM_ID", "")
        artViewModel.fetchArtById(itemId)

        initUI()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun initUI() {
        setVisibility()
        getArtItem()
    }

    private fun setVisibility() {
        lifecycleScope.launch {
            artViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.pbItemInfo.visibility = View.VISIBLE
                    binding.artPreview.visibility = View.GONE
                } else {
                    binding.pbItemInfo.visibility = View.GONE
                    binding.artPreview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getArtItem() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                artViewModel.artItem.collect { artItem ->
                    artItem?.let {
                        viewHolder.renderArt(it){ }
                    }
                }
            }
        }
    }
}