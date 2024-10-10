package com.edsondev26.folkloripedia.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.edsondev26.folkloripedia.databinding.FragmentQuestionBinding
import com.edsondev26.folkloripedia.domain.model.QuizModel
import com.edsondev26.folkloripedia.ui.games.adapter.QuizViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var quizViewHolder: QuizViewHolder

    private val quizViewModel by viewModels<QuizViewModel>()

    private var questionIndex = 0
    private val userAnswers = mutableListOf<String>()
    private val questions_amount = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizViewHolder = QuizViewHolder(view)
        quizViewModel.fetchQuiz(questions_amount)
        initUI()
    }

    private fun initUI() {
        setVisibility()
        getQuestionItems()
    }

    private fun getQuestionItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                quizViewModel.quizList.collect { questions ->
                    if (questions.isNotEmpty()) {
                        loadQuestion(questions[questionIndex])
                    }
                }
            }
        }
    }

    private fun setVisibility() {
        lifecycleScope.launch {
            quizViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.pbQuestion.visibility = View.VISIBLE
                    binding.questionsPreview.visibility = View.GONE
                } else {
                    binding.pbQuestion.visibility = View.GONE
                    binding.questionsPreview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadQuestion(question: QuizModel) {
        quizViewHolder.renderQuestion(question) { answer ->
            checkAnswer(answer)
        }
    }

    private fun checkAnswer(selectedAnswer: String) {
        userAnswers.add(selectedAnswer)
        if (questionIndex < quizViewModel.quizList.value.size - 1) {
            questionIndex++
            loadQuestion(quizViewModel.quizList.value[questionIndex])
        } else {
            val score = quizViewModel.calculateScore(userAnswers)
            val accuracy = (score.toFloat() / questions_amount.toFloat()) * 100

            findNavController().navigate(
                QuestionFragmentDirections.actionQuestionFragmentToScoreFragment(accuracy)
            )
        }
    }
}