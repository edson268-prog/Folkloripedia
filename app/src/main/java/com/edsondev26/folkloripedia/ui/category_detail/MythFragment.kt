package com.edsondev26.folkloripedia.ui.category_detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentMythBinding
import com.edsondev26.folkloripedia.ui.category_detail.adapter.MythDetailFragmentViewHolder
import com.edsondev26.folkloripedia.utils.SizeUtils
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
        (activity as? AppCompatActivity)?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.window.statusBarColor = ContextCompat.getColor(it, R.color.black)
            }
        }
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
        setVisibility()
        getMythItem()
        initListeners()
    }

    private fun initListeners() {
        binding.btnMinus.setOnClickListener { SizeUtils.changeFontSize(-2f, binding.tvMythTale) }
        binding.btnPlus.setOnClickListener { SizeUtils.changeFontSize(2f, binding.tvMythTale) }
    }

    private fun setVisibility() {
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

    private fun getMythItem() {
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