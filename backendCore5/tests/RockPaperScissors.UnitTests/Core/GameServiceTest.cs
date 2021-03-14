using NUnit.Framework;
using RockPaperScissors.Core.Dtos;
using RockPaperScissors.Core.Entities;
using RockPaperScissors.Core.Interfaces;
using RockPaperScissors.Core.Exceptions;
using System.Threading.Tasks;

namespace RockPaperScissors.UnitTests.Core
{
    public class GameServiceTest : TestSetupBase
    {
        private IGameService _gameService;

        [SetUp]
        public void Setup()
        {
            _gameService = GetGameService();
        }

        [Test]
        public async Task TestStartGame()
        {
            Game game = await _gameService.StartGame();

            Assert.NotNull(game);
            Assert.That(game.State, Is.EqualTo(GameState.Started), "State of new game must be 'Started'");
            Assert.IsEmpty(game.Moves, "New game cannot have any moves");
        }

        [Test]
        public async Task TestStartGameWithDefaultDefault()
        {
            Game game = await _gameService.StartGame();

            Assert.NotNull(game);
            Assert.That(game.BestOfRounds, Is.EqualTo(3), "Default rounds must be 3");
            Assert.That(game.Mode, Is.EqualTo(GameMode.Classic), "Default game mode muste be 'Classic'");
        }


        [Test]
        public async Task TestStartGameWithSettings()
        {
            GameStartDto startDto = new GameStartDto
            {
                Mode = GameMode.Expanded,
                BestOfRounds = 5
            };
            Game game = await _gameService.StartGame(startDto);

            Assert.NotNull(game);
            Assert.That(game.BestOfRounds, Is.EqualTo(5), "Default rounds must be 5");
            Assert.That(game.Mode, Is.EqualTo(GameMode.Expanded), "Game mode muste be 'Expanded'");
        }

        [Test]
        public void TestStartGameWithEvenRounds()
        {
            GameStartDto startDto = new GameStartDto
            {
                Mode = GameMode.Expanded,
                BestOfRounds = 4
            };
            Assert.ThrowsAsync<InvalidGameSettingsException>(async () => 
                await _gameService.StartGame(startDto),
                "Even rounds in game settings must throw exception");
        }

        [Test]
        public void TestStartGameWithZeroRounds()
        {
            GameStartDto startDto = new GameStartDto
            {
                Mode = GameMode.Classic,
                BestOfRounds = 0
            };
            Assert.ThrowsAsync<InvalidGameSettingsException>(async () => 
                await _gameService.StartGame(startDto),
                "Game with zero rounds must throw exception");
        }

        [Test]
        public void TestGetGameWithNonExistingId()
        {
            int nonExistingId = int.MaxValue;
            Assert.ThrowsAsync<GameNotExistsException>(async () =>
                await _gameService.GetGame(nonExistingId),
                "Non existing game request must throw exception");
        }
    }
}