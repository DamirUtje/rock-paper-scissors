using RockPaperScissors.Core.Entities;

namespace RockPaperScissors.Core.Dtos
{
    public class GameStartDto
    {
        public GameMode Mode { get; set; }
        public int BestOfRounds { get; set; }
    }
}
