using Bogus;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Entities;

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
        
        if(!_context.IncidentCategories.Any())
        {
            throw new Exception("Incident categories are not seeded yet.");
        }
        
        var exampleTitles = LoadExampleTitles();

        var faker = new Faker<Incident>()
            .RuleFor(p => p.Description, f => f.Lorem.Sentence())
            .RuleFor(p => p.Longitude, f => (float)f.Address.Longitude(16.64d, 17.5d))
            .RuleFor(p => p.Latitude, f => (float)f.Address.Latitude(50.85d, 51.35d))
            .RuleFor(p => p.CreatedOn, f => f.Date.Recent())
            .RuleFor(p => p.User, f => f.PickRandom(_context.Users.ToList()))
            .RuleFor(p => p.Category, f => f.PickRandom(_context.IncidentCategories.ToList()))
            .RuleFor(p => p.Title, f => f.PickRandom(exampleTitles));
        
        var incidents = faker.Generate(250);
        
        _context.Incidents.AddRange(incidents);
        _context.SaveChanges();
    }
}