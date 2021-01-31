package de.damirutje.rockpaperscissors.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Move {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private HandSign userSign;
    @NotNull
    private HandSign botSign;
    @NotNull
    private MoveResult result;
    @NotNull
    private int round;

    public Move() {
        super();
    }

    public Move(HandSign userSign, HandSign botSign, MoveResult result) {
        this.userSign = userSign;
        this.botSign = botSign;
        this.result = result;
    }

    public long getId() {
        return this.id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public HandSign getUserSign() {
        return userSign;
    }

    public void setUserSign(HandSign userSign) {
        this.userSign = userSign;
    }

    public HandSign getBotSign() {
        return botSign;
    }

    public void setBotSign(HandSign botSign) {
        this.botSign = botSign;
    }

    public MoveResult getResult() {
        return result;
    }

    public void setResult(MoveResult result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return id == move.id && round == move.round
                && userSign == move.userSign && botSign == move.botSign && result == move.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userSign, botSign, result);
    }

    @Override
    public String toString() {
        return String.format(
                "Move[id=%d, user sign=%s, bot sign='%s', result='%s']",
                id, userSign, botSign, result);
    }

}
