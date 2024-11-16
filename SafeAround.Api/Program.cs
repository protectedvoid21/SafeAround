using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Models;
using SafeAround.Api.Seeders;
using SafeAround.Api.Services;

var builder = WebApplication.CreateBuilder(args);

AppContext.SetSwitch("Npgsql.EnableLegacyTimestampBehavior", true);

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddAuthentication();
builder.Services.AddAuthorization();

builder.Services.AddNpgsql<SafeAroundDbContext>(builder.Configuration.GetConnectionString("SafeAround"), null,
    dbConfig =>
    {
        dbConfig.UseSnakeCaseNamingConvention();
        if (builder.Environment.IsDevelopment())
        {
            dbConfig.EnableSensitiveDataLogging();
        }
    });

builder.Services.AddIdentity<AppUser, IdentityRole<Guid>>(options =>
    {
        options.Password.RequireDigit = true;
        options.Password.RequireLowercase = true;
        options.Password.RequireUppercase = true;
        options.Password.RequireNonAlphanumeric = true;
        options.Password.RequiredLength = 8;
    })
    .AddEntityFrameworkStores<SafeAroundDbContext>();

builder.Services
    .AddScoped<IncidentService>()
    .AddScoped<UserSeeder>()
    .AddScoped<IncidentSeeder>()
    .AddScoped<Seeder>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.MapEndpoints();

app.UseAuthentication();
app.UseAuthorization();

if(app.Environment.IsProduction())
{
    app.UseHttpsRedirection();
}

var scope = app.Services.CreateScope();
var seeder = scope.ServiceProvider.GetRequiredService<Seeder>();
seeder.Seed();

app.Run();