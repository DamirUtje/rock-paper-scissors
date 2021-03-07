using Microsoft.Extensions.DependencyInjection;
using RockPaperScissors.Core.Interfaces;
using RockPaperScissors.Core.Services;

namespace RockPaperScissors.Core
{
    public static class StartupInjector
    {
        public static void AddGameService(this IServiceCollection services)
        {
            services.AddScoped<IGameService, GameService>();
        }
    }
}
