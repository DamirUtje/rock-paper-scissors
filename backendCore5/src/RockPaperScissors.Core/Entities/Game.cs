
using RockPaperScissors.Core.Shared;
using System.Collections.Generic;

namespace RockPaperScissors.Core.Entities
{
    public class Game : BaseEntity
    {
        public Game()
        {

        }

        public Game(GameMode mode, int rounds)
        {
            Mode = mode;
            BestOfRounds = rounds;
            Moves = new List<Move>();
            State = GameState.Started;
        }

        public GameMode Mode { get; set; }
        public int BestOfRounds { get; set; }
        public GameState State { get; set; }

        public virtual List<Move> Moves { get; set; }
    }
}
