using Bogus;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Models;

namespace SafeAround.Api.Seeders;

public class IncidentSeeder : ISeeder
{
    private readonly SafeAroundDbContext _context;

    public IncidentSeeder(SafeAroundDbContext context)
    {
        _context = context;
    }

    private string[] LoadExampleTitles()
    {
        return File.ReadAllLines("Seeders/Resources/incident_titles.txt");
    }

    public void Seed()
    {
        if(_context.Incidents.Any())
        {
            return;
        }
        
        var exampleTitles = LoadExampleTitles();

        var faker = new Faker<Incident>()
            .RuleFor(p => p.OccurrenceDate, f => f.Date.Past().AddDays(-5))
            .RuleFor(p => p.Description, f => f.Lorem.Sentence())
            .RuleFor(p => p.Longitude, f => (float)f.Address.Longitude(16.8d, 17.2d))
            .RuleFor(p => p.Latitude, f => (float)f.Address.Latitude(51d, 51.19d))
            .RuleFor(p => p.CreatedOn, f => f.Date.Recent())
            .RuleFor(p => p.User, f => f.PickRandom(_context.Users.ToList()))
            .RuleFor(p => p.Title, f => f.PickRandom(exampleTitles));
        
        var incidents = faker.Generate(250);
        
        _context.Incidents.AddRange(incidents);
        _context.SaveChanges();
    }
}