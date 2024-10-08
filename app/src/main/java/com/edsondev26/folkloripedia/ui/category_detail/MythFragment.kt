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
import com.edsondev26.folkloripedia.databinding.FragmentMythBinding
import com.edsondev26.folkloripedia.ui.category_detail.adapter.MythDetailFragmentViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MythFragment : Fragment() {
    private val mythViewModel by viewModels<CategoryDetailFragmentViewModel>()

    private var _binding: FragmentMythBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewHolder: MythDetailFragmentViewHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMythBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewHolder = MythDetailFragmentViewHolder(view)
        val itemId = requireArguments().getString("ITEM_ID", "")
        mythViewModel.fetchMythById(itemId)

        initUI()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun initUI() {
        getMythItem()
        initUIState()
    }

    private fun getMythItem() {
        lifecycleScope.launch {
            mythViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.pbItemInfo.visibility = View.VISIBLE
                    binding.mythPreview.visibility = View.GONE
                } else {
                    binding.pbItemInfo.visibility = View.GONE
                    binding.mythPreview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mythViewModel.mythItem.collect { mythItem ->
                    mythItem?.let {
                        viewHolder.renderMyth(it){ }
                    }
                }
            }
        }
    }
}