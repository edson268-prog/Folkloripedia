package com.edsondev26.folkloripedia.ui.games.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.edsondev26.folkloripedia.databinding.FragmentQuestionBinding
import com.edsondev26.folkloripedia.domain.model.QuizModel
import com.squareup.picasso.Picasso

class QuizViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val bindingQuiz = FragmentQuestionBinding.bind(view)

    fun renderQuestion(quizItem: QuizModel, onAnswerSelected: (String) -> Unit) {
        Picasso.get().load(quizItem.img).into(bindingQuiz.ivQuizImage)
        bindingQuiz.tvQuestion.text = quizItem.question
        bindingQuiz.btnAnswerA.text = quizItem.answerA
        bindingQuiz.btnAnswerB.text = quizItem.answerB
        bindingQuiz.btnAnswerC.text = quizItem.answerC

        bindingQuiz.btnAnswerA.setOnClickListener { onAnswerSelected("A") }
        bindingQuiz.btnAnswerB.setOnClickListener { onAnswerSelected("B") }
        bindingQuiz.btnAnswerC.setOnClickListener { onAnswerSelected("C") }
    }
}