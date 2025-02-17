using Carter;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Entities;
using SafeAround.Api.Seeders;
using SafeAround.Api.Services;
using Serilog;

var builder = WebApplication.CreateBuilder(args);

AppContext.SetSwitch("Npgsql.EnableLegacyTimestampBehavior", true);

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddSerilog(config =>
{
    config.WriteTo.Console();
    config.WriteTo.File("logs/log-.txt", rollingInterval: RollingInterval.Day);
});

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

builder.Services.AddCarter();

builder.Services
    .AddScoped<IncidentService>()
    .AddScoped<UserSeeder>()
    .AddScoped<IncidentSeeder>()
    .AddScoped<IncidentCategorySeeder>()
    .AddScoped<CommentSeeder>()
    .AddScoped<Seeder>();

var app = builder.Build();

app.Map("/", () => Results.Redirect("/swagger"));

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.MapCarter();
app.UseAuthentication();
app.UseAuthorization();

app.UseSerilogRequestLogging();

if(app.Environment.IsProduction())
{
    app.UseHttpsRedirection();
}

var scope = app.Services.CreateScope();
var seeder = scope.ServiceProvider.GetRequiredService<Seeder>();
seeder.Seed();

app.Run(args[0]);