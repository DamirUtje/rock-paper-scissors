package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.model.*;
import de.damirutje.rockpaperscissors.repository.IGameRepository;
import de.damirutje.rockpaperscissors.repository.IMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class GameServiceImpl implements IGameService {

    private final IGameRepository gameRepository;
    private final IMoveRepository moveRepository;

    @Autowired
    public GameServiceImpl(IGameRepository gameRepository,
                           IMoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    @Override
    public Game getGame(long id) {
        return this.getGameFromDb(id);
    }

    @Override
    public long startGame() {
        Game game = new Game(GameMode.Classic, 3);
        return this.getPersistedGameId(game);
    }

    @Override
    public long startGame(GameStartDto gameStartDto) {
        Game game = new Game(gameStartDto.getMode(), gameStartDto.getRounds());
        return this.getPersistedGameId(game);
    }

    @Override
    public Game makeMove(long gameId, HandSign handSign) {
        Game game = this.getGameFromDb(gameId);
        if (game.getState() == GameState.Started) {
            Move move = this.getMove(game, handSign);
            game.setCurrentMove(move);
            this.gameRepository.save(game);
        } // else throw Exception
        return game;
    }

    private long getPersistedGameId(Game game) {
        Game persistedGame = this.gameRepository.save(game);
        return persistedGame.getId();
    }

    private Game getGameFromDb(long gameId) {
        Game game = null;
        Optional<Game> dbGame = this.gameRepository.findById(gameId);

        if (dbGame.isPresent()) {
            game = dbGame.get();
        } // throw new Exception("Not found");
        return game;
    }

    private Move getMove(Game game, HandSign userSign) {
        HandSign botSign = getBotSign(game);
        Result result = getMoveResult(userSign, botSign);
        Move move = new Move(userSign, botSign, result);
        this.moveRepository.save(move);
        return move;
    }

    private Result getMoveResult(HandSign userShape, HandSign botShape) {
        var result = Result.Draw;

        if (userShape.isBetterThan(botShape)) {
            result = Result.Win;
        } else if(botShape.isBetterThan(userShape)) {
            result = Result.Loose;
        }
        return result;
    }

    private HandSign getBotSign(Game game) {
        Random random = new Random();
        HandSign[] handSigns = game.getAvailableSigns();
        return handSigns[random.nextInt(handSigns.length)];
    }
}
