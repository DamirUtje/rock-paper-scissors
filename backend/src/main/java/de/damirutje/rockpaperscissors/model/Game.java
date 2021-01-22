package de.damirutje.rockpaperscissors.model;

public class Game {

    private Move currentMove;

    public Game() {

    }

    public Move getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(Move currentMove) {
        this.currentMove = currentMove;
    }

}
