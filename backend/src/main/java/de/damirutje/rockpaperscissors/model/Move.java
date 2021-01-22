package de.damirutje.rockpaperscissors.model;

public class Move {

    private HandSign userSign;
    private HandSign botSign;
    private Result result;

    public Move() {
        super();
    }

    public Move(HandSign userSign, HandSign botSign, Result result) {
        this.userSign = userSign;
        this.botSign = botSign;
        this.result = result;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Move [userShape=" + userSign + ", botShape=" + botSign + ", result=" + result + "]";
    }

}
