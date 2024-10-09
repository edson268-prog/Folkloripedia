package com.edsondev26.folkloripedia.ui.games

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentQuestionBinding
import com.edsondev26.folkloripedia.domain.model.CategoryItemModel
import com.edsondev26.folkloripedia.domain.model.QuizModel
import com.edsondev26.folkloripedia.ui.category_detail.CategoryDetailFragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionFragment : Fragment() {
    private lateinit var binding: FragmentQuestionBinding
    private val quizViewModel by viewModels<QuizViewModel>()

    private var questionIndex = 0
    private val userAnswers = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizViewModel.fetchQuiz(3)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                quizViewModel.quizList.collect { questions ->
                    if (questions.isNotEmpty()) {
                        loadQuestion(questions[questionIndex])
                    }
                }
            }
        }

        binding.btnAnswerA.setOnClickListener { checkAnswer("A") }
        binding.btnAnswerB.setOnClickListener { checkAnswer("B") }
        binding.btnAnswerC.setOnClickListener { checkAnswer("C") }
    }

    private fun loadQuestion(question: QuizModel) {
        binding.tvQuestion.text = question.question
        binding.btnAnswerA.text = question.answerA
        binding.btnAnswerB.text = question.answerB
        binding.btnAnswerC.text = question.answerC
        // Cargar imagen y sonido si es necesario
    }

    private fun checkAnswer(selectedAnswer: String) {
        userAnswers.add(selectedAnswer)
        if (questionIndex < quizViewModel.quizList.value.size - 1) {
            questionIndex++
            loadQuestion(quizViewModel.quizList.value[questionIndex])
        } else {
            val score = quizViewModel.calculateScore(userAnswers)
//            navigateToScore(score)

//            findNavController().navigate(
//                QuestionFragmentDirections.actionQuestionFragmentToScoreFragment(score)
//            )

            findNavController().navigate(
                QuestionFragmentDirections.actionQuestionFragmentToScoreFragment(score)
            )
        }
    }
}