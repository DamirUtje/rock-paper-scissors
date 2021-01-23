package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.NewGameDto;
import de.damirutje.rockpaperscissors.model.*;
import de.damirutje.rockpaperscissors.repository.IGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class GameServiceImpl implements IGameService {

    private final IGameRepository gameRepository;

    @Autowired
    public GameServiceImpl(IGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game getCurrentGame(long gameId, HandSign handSign) {

        Game currentGame = null;
        Optional<Game> dbGame = this.gameRepository.findById(gameId);

        if (dbGame.isPresent()) {

            Move move = getCurrentMove(handSign);
            currentGame = dbGame.get();

        }  else {
            //throw new Exception("Not found");
        }

        return currentGame;
    }

    @Override
    public long startGameDefault() {

        Game game = new Game(Mode.Classic, 3);

        return this.getPersistedGameId(game);
    }

    @Override
    public long startGame(NewGameDto newGameDto) {

        Game game = new Game(newGameDto.getMode(), newGameDto.getRounds());

        return this.getPersistedGameId(game);
    }

    private long getPersistedGameId(Game game) {
        Game persistedGame = this.gameRepository.save(game);
        return persistedGame.getId();
    }

    private Move getCurrentMove(HandSign userSign) {

        HandSign botSign = getBotSign();
        Result result = getUserResult(userSign, botSign);

        return new Move(userSign, botSign, result);
    }

    private Result getUserResult(HandSign userShape, HandSign botShape) {
        var result = Result.Draw;

        if (userShape.isBetterThan(botShape)) {
            result = Result.Win;
        } else if(botShape.isBetterThan(userShape)) {
            result = Result.Loose;
        }

        return result;
    }

    /**
     * Pick a random value of the {@link HandSign} enum.
     * @return a random {@link HandSign}.
     */
    private HandSign getBotSign() {

        // handle Well...
        Random random = new Random();
        var handSigns = HandSign.values();
        return handSigns[random.nextInt(handSigns.length)];
    }
}
