package de.damirutje.rockpaperscissors.dto;

import de.damirutje.rockpaperscissors.model.GameMode;

public class GameStartDto {
    private GameMode mode;
    private int rounds;

    public GameStartDto() {
    }

    public GameStartDto(GameMode mode, int rounds) {
        this.mode = mode;
        this.rounds = rounds;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
