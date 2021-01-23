package de.damirutje.rockpaperscissors.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id;

    private Mode mode;
    private int rounds;
    //private Move currentMove;
    //private HashSet<HandSign> availableSigns;

    public Game() {
    }

    public Game(Mode mode, int rounds) {
        this.mode = mode;
        this.rounds = rounds;
    }

    public long getId() {
        return id;
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

    /*
    public Move getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(Move currentMove) {
        this.currentMove = currentMove;
    }

     */

    // TODO: overwrite hashCode, equals, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && rounds == game.rounds && mode == game.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, rounds);
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", mode=" + mode + ", rounds=" + rounds + "]";
    }
}
