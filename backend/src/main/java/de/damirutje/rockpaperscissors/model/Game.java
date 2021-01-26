package de.damirutje.rockpaperscissors.model;

import org.apache.commons.lang3.ArrayUtils;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id;
    private GameMode mode;
    private int bestOfRounds;
    @ElementCollection
    private Set<Move> moves;
    private GameState state;

    @Transient
    private HandSign[] availableSigns;

    public Game() {
        super();
    }

    public Game(GameMode mode, int bestOfRounds) {
        this.mode = mode;
        this.bestOfRounds = bestOfRounds;
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

    public Set<Move> getMoves() {
        return this.moves;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void setCurrentMove(Move currentMove) {
        if (this.moves.size() < this.bestOfRounds) {
            this.moves.add(currentMove);
            currentMove.setRound(this.moves.size());
        }

        if (currentMove.getRound() >= this.bestOfRounds) {
            this.state = GameState.Finished;
        }
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
        return id == game.id && bestOfRounds == game.bestOfRounds && mode == game.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, bestOfRounds);
    }

    @Override
    public String toString() {
        return String.format(
                "Game[id=%d, mode='%s', rounds='%s']",
                id, mode, bestOfRounds);
    }
}
