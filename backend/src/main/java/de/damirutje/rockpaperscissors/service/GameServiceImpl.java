package de.damirutje.rockpaperscissors.service;

import de.damirutje.rockpaperscissors.dto.GameStartDto;
import de.damirutje.rockpaperscissors.exception.GameNotExistException;
import de.damirutje.rockpaperscissors.exception.GameReadonlyException;
import de.damirutje.rockpaperscissors.exception.InvalidGameMoveException;
import de.damirutje.rockpaperscissors.exception.InvalidGameSettingsException;
import de.damirutje.rockpaperscissors.model.*;
import de.damirutje.rockpaperscissors.repository.GameRepository;
import de.damirutje.rockpaperscissors.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private static final GameMode defaultGameMode = GameMode.Classic;
    private static final int defaultBestOfRounds = 3;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, MoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    @Override
    public long startGame() {
        Game game = new Game(defaultGameMode, defaultBestOfRounds);
        return this.getPersistedGameId(game);
    }

    @Override
    public long startGame(GameStartDto gameStartDto) {
        int bestOfRounds = gameStartDto.getBestOfRounds();
        if (bestOfRounds % 2 > 0) {
            Game game = new Game(gameStartDto.getMode(), gameStartDto.getBestOfRounds());
            return this.getPersistedGameId(game);
        } else {
            throw new InvalidGameSettingsException("The best-of game mode expects an odd number of rounds");
        }
    }

    @Override
    public Game getGame(long id) {
        return this.getGameFromDb(id);
    }

    @Override
    public Game makeMove(long gameId, HandSign handSign) {
        Game game = this.getGameFromDb(gameId);
        this.validateGameMove(game, handSign);
        Move move = this.getMove(game, handSign);
        this.setCurrentMove(game, move);
        this.gameRepository.save(game);
        return game;
    }

    @Override
    public void abortGame(long id) {
        Game game = this.getGameFromDb(id);
        if (game.getState() == GameState.Started) {
            game.setState(GameState.Aborted);
            this.gameRepository.save(game);
        } else {
            throw new GameReadonlyException(id);
        }
    }

    private void validateGameMove(Game game, HandSign handSign) {
        if (game.getState() != GameState.Started) {
            throw new GameReadonlyException(game.getId());
        }

        if (!Arrays.asList(game.getAvailableSigns()).contains(handSign)) {
            throw new InvalidGameMoveException(game.getMode(), handSign);
        }
    }

    private void setCurrentMove(Game game, Move currentMove) {
        if (this.getGameWins(game) < game.getBestOfRounds()) {
            game.getMoves().add(currentMove);
            currentMove.setRound(game.getMoves().size());
        }

        if (this.getGameWins(game) >= game.getBestOfRounds()) {
            game.setState(GameState.Finished);
        }
    }

    /**
     * Counts the amount of won games for both players.
     * @param game to count wins for
     * @return amount of won games
     */
    private Long getGameWins(Game game) {
        Long gameWins = 0L;
        if (!game.getMoves().isEmpty()) {
            Map<MoveResult, Long> resultDict = game.getMoves()
                    .stream()
                    .filter(move -> move.getResult() != MoveResult.Draw)
                    .collect(Collectors.groupingBy(Move::getResult, Collectors.counting()));

            if (!resultDict.isEmpty()) {
                gameWins = Collections
                        .max(resultDict.entrySet(), Comparator.comparingLong(Map.Entry::getValue))
                        .getValue();
            }
        }
        return gameWins;
    }

    /**
     * Persists a given {@link Game} entity to database and returns its id.
     * @param game entity to save {@link Game}
     * @return id of current {@link Game}
     */
    private long getPersistedGameId(Game game) {
        Game persistedGame = this.gameRepository.save(game);
        return persistedGame.getId();
    }

    /**
     * Requests a {@link Game} entity from database by specified id.
     * @param id of requested {@link Game}
     * @return requested {@link Game}
     */
    private Game getGameFromDb(long id) {
        Game game = null;
        Optional<Game> dbGame = this.gameRepository.findById(id);

        if (dbGame.isPresent()) {
            game = dbGame.get();
        } else {
            throw new GameNotExistException(id);
        }
        return game;
    }

    /**
     * Makes the {@link Move} for current {@link Game}.
     * @param game current game
     * @param userSign form UI
     * @return completed {@link Move}
     */
    private Move getMove(Game game, HandSign userSign) {
        HandSign botSign = getBotSign(game);
        MoveResult result = getMoveResult(userSign, botSign);
        Move move = new Move(userSign, botSign, result);
        this.moveRepository.save(move);
        return move;
    }

    /**
     * Compares thw given {@link HandSign}s to determine move result.
     * @param userSign sign from UI
     * @param botSign random sign
     * @return random {@link HandSign}
     */
    private MoveResult getMoveResult(HandSign userSign, HandSign botSign) {
        var result = MoveResult.Draw;

        if (userSign.isBetterThan(botSign)) {
            result = MoveResult.Win;
        } else if(botSign.isBetterThan(userSign)) {
            result = MoveResult.Loose;
        }
        return result;
    }

    /**
     * Makes a random {@link HandSign} for second player.
     * @param game to make move
     * @return random {@link HandSign}
     */
    private HandSign getBotSign(Game game) {
        Random random = new Random();
        HandSign[] handSigns = game.getAvailableSigns();
        return handSigns[random.nextInt(handSigns.length)];
    }
}
