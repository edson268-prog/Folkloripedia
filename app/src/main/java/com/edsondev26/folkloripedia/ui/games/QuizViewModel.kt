package com.edsondev26.folkloripedia.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsondev26.folkloripedia.domain.GameRepository
import com.edsondev26.folkloripedia.domain.model.QuizModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val gameRepository: GameRepository): ViewModel() {
    private val _quizList = MutableStateFlow<List<QuizModel>>(emptyList())
    val quizList: MutableStateFlow<List<QuizModel>> get() = _quizList

    private var _isLoading = MutableStateFlow(true)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun fetchQuiz(questions: Number) {
        _isLoading.value = true
        viewModelScope.launch {
            gameRepository.getQuizItems(questions).collect { items ->
                _quizList.value = items
                _isLoading.value = false
            }
        }
    }

    fun calculateScore(answers: List<String>): Int {
        var score = 0
        _quizList.value.let { questionList ->
            for ((index, question) in questionList.withIndex()) {
                if (question.rightAnswer == answers[index]) {
                    score++
                }
            }
        }
        return score
    }
}