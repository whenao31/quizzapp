package com.wil.quizzapp;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.wil.quizzapp.entities.Player;

public class PlayerListAdapter  extends ListAdapter<Player, PlayerViewHolder> {

    public PlayerListAdapter(@NonNull DiffUtil.ItemCallback<Player> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PlayerViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player current = getItem(position);
        holder.bind(current);
    }

    static class PlayerDiff extends DiffUtil.ItemCallback<Player> {

        @Override
        public boolean areItemsTheSame(@NonNull Player oldPlayer, @NonNull Player newPlayer) {
            return oldPlayer == newPlayer;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Player oldItem, @NonNull Player newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
