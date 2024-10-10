package com.edsondev26.folkloripedia.ui.category_detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentDanceBinding
import com.edsondev26.folkloripedia.ui.category_detail.adapter.DanceDetailFragmentViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DanceFragment : Fragment() {
    private val danceViewModel by viewModels<CategoryDetailFragmentViewModel>()

    private var _binding: FragmentDanceBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewHolder: DanceDetailFragmentViewHolder

    private var vestmentImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDanceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewHolder = DanceDetailFragmentViewHolder(view)
        val itemId = requireArguments().getString("ITEM_ID", "")
        danceViewModel.fetchDanceById(itemId)

        initUI()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun initUI() {
        setVisibility()
        getDanceItem()
        initListeners()
    }


    private fun setVisibility() {
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

    private fun initListeners() {
        binding.btnShowVestmentImage.setOnClickListener {
            showImageDialog()
        }
    }

    private fun getDanceItem() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                danceViewModel.danceItem.collect { danceItem ->
                    danceItem?.let {
                        vestmentImageUrl = it.vestment
                        viewHolder.renderDance(it){ }
                    }
                }
            }
        }
    }

    private fun showImageDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_image)

        val imageView = dialog.findViewById<ImageView>(R.id.dialogImageView)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.loadingProgressBar)

        Picasso.get()
            .load(vestmentImageUrl)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                    imageView.setImageResource(R.drawable.image_not_available)
                }
            })

        val btnClose = dialog.findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}