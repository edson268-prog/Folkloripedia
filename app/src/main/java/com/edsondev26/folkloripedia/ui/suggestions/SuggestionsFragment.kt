package com.edsondev26.folkloripedia.ui.suggestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.data.SuggestionRepositoryImpl
import com.edsondev26.folkloripedia.databinding.FragmentSuggestionsBinding
import com.edsondev26.folkloripedia.domain.SuggestionRepository
import com.edsondev26.folkloripedia.domain.model.SuggestionModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SuggestionsFragment : Fragment() {
    private lateinit var binding: FragmentSuggestionsBinding
    private lateinit var suggestionRepository: SuggestionRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuggestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suggestionRepository =
            SuggestionRepositoryImpl(FirebaseFirestore.getInstance(), requireContext())
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnSendSuggestion.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val suggestionText = binding.etSuggestion.text.toString()

            if (name.isEmpty() || email.isEmpty() || suggestionText.isEmpty()) {
                Toast.makeText(context, getString(R.string.val_suggestion_form), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)){
                Toast.makeText(context, getString(R.string.val_suggestion_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                suggestionRepository.isAllowedToSend().collect { isAllowed ->
                    if (isAllowed) {
                        val suggestion = SuggestionModel(name, email, suggestionText)
                        suggestionRepository.setSuggestion(suggestion).collect { result ->
                            Toast.makeText(
                                context,
                                getString(R.string.suggestion_saved),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.val_suggestion_time),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    binding.etName.setText("")
                    binding.etEmail.setText("")
                    binding.etSuggestion.setText("")
                }
            }
        }
    }

    fun isValidEmail(str: String): Boolean {
        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }
}