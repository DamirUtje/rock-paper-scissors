package de.damirutje.rockpaperscissors.utils;

import de.damirutje.rockpaperscissors.model.Game;
import de.damirutje.rockpaperscissors.model.HandSign;
import de.damirutje.rockpaperscissors.model.MoveResult;
import java.util.Random;

public class GameHelper {

    /**
     * Makes a random {@link HandSign} for second player.
     * @param game to make move
     * @return random {@link HandSign}
     */
    public static HandSign getBotSign(Game game) {
        Random random = new Random();
        HandSign[] handSigns = game.getAvailableSigns();
        return handSigns[random.nextInt(handSigns.length)];
    }

    /**
     * Compares thw given {@link HandSign}s to determine move result.
     * @param userSign sign from UI
     * @param botSign random sign
     * @return random {@link HandSign}
     */
    public static MoveResult getMoveResult(HandSign userSign, HandSign botSign) {
        var result = MoveResult.Draw;

        if (userSign.isBetterThan(botSign)) {
            result = MoveResult.Win;
        } else if(botSign.isBetterThan(userSign)) {
            result = MoveResult.Lose;
        }
        return result;
    }
}
