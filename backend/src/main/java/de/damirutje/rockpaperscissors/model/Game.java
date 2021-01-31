package de.damirutje.rockpaperscissors.model;

import org.apache.commons.lang3.ArrayUtils;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private GameMode mode;
    private int bestOfRounds;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Move> moves;
    @NotNull
    private  GameState state;

    @Transient
    private HandSign[] availableSigns;

    public Game() {
        super();
    }

    public Game(GameMode mode, int bestOfRounds) {
        this.mode = mode;
        this.bestOfRounds = bestOfRounds;
        this.moves = new ArrayList<>();
        this.state = GameState.Started;
    }

    public long getId() {
        return this.id;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public int getBestOfRounds() {
        return this.bestOfRounds;
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public HandSign[] getAvailableSigns() {
        this.availableSigns = HandSign.values();

        if (this.mode == GameMode.Classic) {
            this.availableSigns = ArrayUtils.removeElement(availableSigns, HandSign.Well);
        }
        return this.availableSigns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && bestOfRounds == game.bestOfRounds
                && mode == game.mode && Objects.equals(moves, game.moves) && state == game.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, bestOfRounds, moves, state);
    }

    @Override
    public String toString() {
        return String.format(
                "Game[id=%d, mode='%s', bestOfRounds='%s', state='%s']",
                id, mode, bestOfRounds, state);
    }
}
