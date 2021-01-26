package de.damirutje.rockpaperscissors.dto;

import de.damirutje.rockpaperscissors.model.GameMode;

public class GameStartDto {
    private GameMode mode;
    private int bestOfRounds;

    public GameStartDto() {
    }

    public GameStartDto(GameMode mode, int bestOfRounds) {
        this.mode = mode;
        this.bestOfRounds = bestOfRounds;
    }

    public GameMode getMode() {
        return mode;
    }

    public int getBestOfRounds() {
        return bestOfRounds;
    }
}
