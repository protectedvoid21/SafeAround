namespace SafeAround.Api.Seeders;

public class Seeder
{
    private readonly ISeeder[] _seeders;

    public Seeder(
        UserSeeder userSeeder,
        IncidentSeeder incidentSeeder,
        IncidentCategorySeeder incidentCategorySeeder
    )
    {
        _seeders = [userSeeder, incidentCategorySeeder, incidentSeeder];
    }

    public void Seed()
    {
        foreach (var seeder in _seeders)
        {
            seeder.Seed();
        }
    }
}