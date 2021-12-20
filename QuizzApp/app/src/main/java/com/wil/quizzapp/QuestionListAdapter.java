package com.wil.quizzapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.wil.quizzapp.entities.Question;

public class QuestionListAdapter extends ListAdapter<Question, QuestionViewHolder> {

    public QuestionListAdapter(@NonNull DiffUtil.ItemCallback<Question> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QuestionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question current = getItem(position);
        holder.bind(current);
    }

    static class QuestionDiff extends DiffUtil.ItemCallback<Question> {

        @Override
        public boolean areItemsTheSame(@NonNull Question oldItem, @NonNull Question newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Question oldItem, @NonNull Question newItem) {
            return oldItem.getText().equalsIgnoreCase(newItem.getText());
        }
    }

}
