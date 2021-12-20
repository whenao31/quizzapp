package com.wil.quizzapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wil.quizzapp.entities.Player;

public class PlayerViewHolder extends RecyclerView.ViewHolder {

    private final TextView playerName;
    private final TextView playerScore;
    private final TextView playerLevel;

    private PlayerViewHolder (View itemView) {
        super(itemView);
        playerName = itemView.findViewById(R.id.tv_player_name);
        playerScore = itemView.findViewById(R.id.tv_player_score);
        playerLevel = itemView.findViewById(R.id.tv_player_level);
    }

    public void bind(Player player) {
        playerName.setText(player.getName());
        playerScore.setText(Integer.toString(player.getScore()));
        playerLevel.setText(player.getLevel());
    }

    static PlayerViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_card_view, parent, false);
        return new PlayerViewHolder(view);
    }
}
