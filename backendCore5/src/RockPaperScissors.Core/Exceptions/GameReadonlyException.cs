using System;

namespace RockPaperScissors.Core.Exceptions
{
    public class GameReadonlyException : Exception
    {
        public GameReadonlyException(string message) : base(message)
        {
        }
    }
}
