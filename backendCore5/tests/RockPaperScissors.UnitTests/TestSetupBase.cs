using Microsoft.EntityFrameworkCore;
using NUnit.Framework;
using RockPaperScissors.Core.Interfaces;
using RockPaperScissors.Core.Services;
using RockPaperScissors.Infrastructure.Data;

namespace RockPaperScissors.UnitTests
{
    public class TestSetupBase
    {
        private IGameService _gameService;
        private IRepository _repository;

        [SetUp]
        public void BaseSetup()
        {
            var options = new DbContextOptionsBuilder<AppDbContext>()
                .UseInMemoryDatabase(databaseName: "AppTestDatabase")
                .Options;

            _repository = new Repository(new AppDbContext(options));
            _gameService = new GameService(_repository);
        }

        internal virtual IGameService GetGameService()
        {
            return _gameService;
        }
    }
}
