package de.damirutje.rockpaperscissors.dto;

import de.damirutje.rockpaperscissors.model.GameMode;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStartDto that = (GameStartDto) o;
        return bestOfRounds == that.bestOfRounds && mode == that.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode, bestOfRounds);
    }
}
