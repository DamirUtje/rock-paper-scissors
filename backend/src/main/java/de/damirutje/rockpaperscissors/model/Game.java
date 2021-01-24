package de.damirutje.rockpaperscissors.model;

import org.apache.commons.lang3.ArrayUtils;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private long id;
    private GameMode mode;
    private int rounds;
    private GameState state;
    @ElementCollection
    private final Set<Move> moves = new HashSet<>();

    @Transient
    private Result result;
    @Transient
    private Move currentMove;
    @Transient
    private HandSign[] availableSigns;

    public Game() {
        super();
    }

    public Game(GameMode mode, int rounds) {
        this.mode = mode;
        this.rounds = rounds;
        this.state = GameState.Started;
    }

    public long getId() {
        return this.id;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public int getRounds() {
        return this.rounds;
    }

    public GameState getState() {
        return this.state;
    }

    public Set<Move> getMoves() {
        return this.moves;
    }

    public Move getCurrentMove() {
        return this.currentMove;
    }

    public void setCurrentMove(Move currentMove) {
        if (this.moves.size() < this.rounds) {
            this.currentMove = currentMove;
            this.moves.add(currentMove);
            currentMove.setRound(this.moves.size());
        }

        if (currentMove.getRound() >= this.rounds) {
            this.state = GameState.Finished;
        }
    }

    public Result getResult() {
        if (this.state == GameState.Finished) {
            long winsCount = this.moves.stream()
                    .filter(move -> move.getResult().equals(Result.Win))
                    .count();

            long loosesCount = this.moves.stream()
                    .filter(move -> move.getResult().equals(Result.Loose))
                    .count();

            this.result = Result.Draw;
            if (winsCount > loosesCount) {
                this.result = Result.Win;
            } else if(winsCount < loosesCount) {
                this.result = Result.Loose;
            }
        }
        return result;
    }

    public HandSign[] getAvailableSigns() {
        this.availableSigns = HandSign.values();

        if (this.mode == GameMode.Classic) {
            this.availableSigns = ArrayUtils.removeElement(availableSigns, HandSign.Well);
        }
        return this.availableSigns;
    }

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
        return String.format(
                "Game[id=%d, mode='%s', rounds='%s']",
                id, mode, rounds);
    }
}
