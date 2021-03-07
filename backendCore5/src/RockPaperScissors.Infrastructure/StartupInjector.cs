using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using RockPaperScissors.Infrastructure.Data;
using RockPaperScissors.Core.Interfaces;

namespace RockPaperScissors.Infrastructure
{
    public static class StartupInjector
    {
        public static void AddDbContext(this IServiceCollection services, string connectionString)
        {
            services.AddDbContext<AppDbContext>(options =>
                options.UseSqlite(connectionString));
        }

        public static void AddRepository(this IServiceCollection services)
        {
            services.AddScoped<IRepository, Repository>();
        }
    }
}
