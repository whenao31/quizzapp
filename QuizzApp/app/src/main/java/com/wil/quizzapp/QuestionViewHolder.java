package com.wil.quizzapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wil.quizzapp.entities.Question;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    private final TextView questionId;
    private final TextView questionText;
    private final TextView questionAns1;
    private final TextView questionAns2;
    private final TextView questionAns3;
    private final TextView questionAns4;
    private final TextView questionCorrectAns;
    private final TextView questionLevel;

    private QuestionViewHolder(View itemView) {
        super(itemView);
        questionId = itemView.findViewById(R.id.tv_review_q_title);
        questionText = itemView.findViewById(R.id.tv_question_text);
        questionAns1 = itemView.findViewById(R.id.tv_answer1);
        questionAns2 = itemView.findViewById(R.id.tv_answer2);
        questionAns3 = itemView.findViewById(R.id.tv_answer3);
        questionAns4 = itemView.findViewById(R.id.tv_answer4);
        questionCorrectAns = itemView.findViewById(R.id.tv_correct_ans);
        questionLevel = itemView.findViewById(R.id.tv_q_level);
    }

    public void bind(Question question) {
        questionId.setText(Integer.toString(question.getId()));
        questionText.setText(question.getText());
        questionAns1.setText(question.getAnswer1());
        questionAns2.setText(question.getAnswer2());
        questionAns3.setText(question.getAnswer3());
        questionAns4.setText(question.getAnswer4());
        questionCorrectAns.setText(Integer.toString(question.getCorrectAnswer()));
        questionLevel.setText(Integer.toString(question.getLevel()));
    }

    static QuestionViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card_view, parent, false);
        return new QuestionViewHolder(view);
    }
}
