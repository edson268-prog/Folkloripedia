package com.edsondev26.folkloripedia.ui.suggestions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentQuestionBinding
import com.edsondev26.folkloripedia.databinding.FragmentSuggestionsBinding

class SuggestionsFragment : Fragment() {
    private lateinit var binding: FragmentSuggestionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuggestionsBinding.inflate(inflater, container, false)
        return binding.root
    }
}