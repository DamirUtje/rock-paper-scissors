package de.damirutje.rockpaperscissors.dto;

import de.damirutje.rockpaperscissors.model.Mode;

public class NewGameDto {
    private Mode mode;
    private int rounds;

    public NewGameDto() {
    }

    public NewGameDto(Mode mode, int rounds) {
        this.mode = mode;
        this.rounds = rounds;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
